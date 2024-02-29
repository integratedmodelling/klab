package org.integratedmodelling.klab.hub.emails.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.emails.dto.EmailTemplate;
import org.integratedmodelling.klab.hub.repository.EmailTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {
	
	private final MongoTemplate mongoTemplate;
	
	@Autowired
	EmailTemplateRepository emailTemplateRepository;
	
    @Autowired
    public EmailTemplateServiceImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


	@Override
	public Collection<String> getEmailTemplateNames() {
		Collection<EmailTemplate> emailTemplates = emailTemplateRepository.findAll();
		Collection<String> emailTemplateNames = new HashSet<>();
		for(EmailTemplate emailTemplate: emailTemplates) {
			emailTemplateNames.add(emailTemplate.getId());
		}
		return emailTemplateNames;
	}

	@Override
	public Collection<EmailTemplate> getEmailTemplatesByAuthorUsername(String username) {
		return emailTemplateRepository.findByAuthorUsername(username);
	}

	@Override
	public EmailTemplate create(EmailTemplate model) {
		mongoTemplate.save(model);
		Logging.INSTANCE.info("Created Mongo Email template: " + model.toString());
		return model;
	}

	@Override
	public EmailTemplate update(EmailTemplate model) {
		Optional<EmailTemplate> existingEmailTemplate = emailTemplateRepository.findById(model.getId());
		if (existingEmailTemplate.isPresent()) {
			mongoTemplate.save(model);
		} else {
			Logging.INSTANCE.warn("Email template with this id doesn't exists, no modifications");
		}
		return model;
	}

	@Override
	public void delete(String id) {
		Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findById(id);
		if (emailTemplate.isPresent()) {
			mongoTemplate.remove(emailTemplate);
			Logging.INSTANCE.info("Deleted Mongo Email Template: " + emailTemplate.get().getName());
		} else {
			Logging.INSTANCE.warn("Email template with this id doesn't exists, no deletions");
		}
	}

	@Override
	public Collection<EmailTemplate> getAll() {
		return mongoTemplate.findAll(EmailTemplate.class);
	}

	@Override
	public EmailTemplate getByName(String name) {
		return emailTemplateRepository.findByName(name).get();
	}

	@Override
	public boolean exists(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EmailTemplate getById(String id) {
		return emailTemplateRepository.findById(id).get();
	}

}
