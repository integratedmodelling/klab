package org.integratedmodelling.klab.hub.licenses.services;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Collection;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.licenses.dto.BouncyConfiguration;
import org.integratedmodelling.klab.hub.licenses.dto.LicenseConfiguration;
import org.integratedmodelling.klab.hub.licenses.exceptions.LicenseConfigDefaultNotFound;
import org.integratedmodelling.klab.hub.licenses.exceptions.LicenseConfigDoestNotExists;
import org.integratedmodelling.klab.hub.licenses.exceptions.LicenseGenerationError;
import org.integratedmodelling.klab.hub.nodes.commands.GenerateLicenseFactory;
import org.integratedmodelling.klab.hub.repository.LicenseConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseConfigServiceImpl implements LicenseConfigService{
	
	private LicenseConfigRepository repository;
	
	@Autowired public LicenseConfigServiceImpl(LicenseConfigRepository repository) {
		this.repository = repository;
	}

	@Override
	public LicenseConfiguration create(LicenseConfiguration model) {
		LicenseConfiguration config;
		try {
			config = new GenerateLicenseFactory()
				.getConfiguration(BouncyConfiguration.class);
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException | PGPException
				| IOException e) {
			throw new LicenseGenerationError(model.getName());
		}
		config.setName(model.getName());
		return repository.insert(config);
	}

	@Override
	public LicenseConfiguration update(LicenseConfiguration model) {
		return repository.save(model);
	}

	@Override
	public void delete(String name) {
		repository.findByNameIgnoreCase(name).ifPresent(config -> {
			repository.delete(config);
		});		
	}

	@Override
	public Collection<LicenseConfiguration> getAll() {
		return repository.findAll();
	}

	@Override
	public LicenseConfiguration getByName(String name) {
		return repository.findByNameIgnoreCase(name)
				.orElseThrow(() -> new LicenseConfigDoestNotExists(name));
	}

	@Override
	public LicenseConfiguration getById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new LicenseConfigDoestNotExists(id));
	}

	@Override
	public boolean exists(String name) {
		return repository.findByNameIgnoreCase(name).isPresent();
	}

	@Override
	public LicenseConfiguration getDefaultConfig() {
		return repository.findByDefaultConfigIsTrue()
				.orElseThrow(() -> new LicenseConfigDefaultNotFound());
	}

	@Override
	public LicenseConfiguration setAsDefaultConfig(String name) {
		repository.findAll().forEach(config -> {
			config.setDefaultConfig(false);
			update(config);
		});
		LicenseConfiguration config = getByName(name);
		config.setDefaultConfig(true);
		return update(config);
	}

	@Override
	public LicenseConfiguration getConfigByKey(String key) {
		return repository.findByKeyString(key).orElseThrow(() -> new LicenseConfigDoestNotExists(key));
	}

}
