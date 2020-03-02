package org.integratedmodelling.klab.hub.emails.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.hub.emails.EmailTemplate;
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
	public void createEmailTemplate(EmailTemplate emailTemplate) {
		mongoTemplate.save(emailTemplate);
		Logging.INSTANCE.info("Created Mongo Email template: " + emailTemplate.toString());
	}

	@Override
	public void updateEmailTemplate(String id, EmailTemplate emailTemplate) {
		Optional<EmailTemplate> existingEmailTemplate = emailTemplateRepository.findById(id);
		if (existingEmailTemplate.isPresent()) {
			mongoTemplate.save(emailTemplate);
		} else {
			Logging.INSTANCE.warn("Email template with this id doesn't exists, no modifications");
		}
	}

	@Override
	public void deleteEmailTemplate(String id) {
		Optional<EmailTemplate> emailTemplate = emailTemplateRepository.findById(id);
		if (emailTemplate.isPresent()) {
			mongoTemplate.remove(emailTemplate);
			Logging.INSTANCE.info("Deleted Mongo Email Template: " + emailTemplate.get().getName());
		} else {
			Logging.INSTANCE.warn("Email template with this id doesn't exists, no deletions");
		}
	}

	@Override
	public Collection<EmailTemplate> getEmailTemplates() {
		return mongoTemplate.findAll(EmailTemplate.class);
	}

	@Override
	public Optional<EmailTemplate> getEmailTemplate(String id) {
		return emailTemplateRepository.findById(id);
		
	}
	
	@Override
	public Collection<EmailTemplate> getEmailTemplatesByName(String name) {
		return emailTemplateRepository.findByName(name);
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

}
