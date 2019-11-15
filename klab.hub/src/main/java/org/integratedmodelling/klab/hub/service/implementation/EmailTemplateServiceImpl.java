package org.integratedmodelling.klab.hub.service.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.models.EmailTemplate;
import org.integratedmodelling.klab.hub.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {
	
	private final MongoTemplate mongoTemplate;
	
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
		Query query = new Query(Criteria.where("id").is(id));
		List<EmailTemplate> found = mongoTemplate.find(query, EmailTemplate.class);
		if (found.size() == 1) {
			mongoTemplate.save(emailTemplate);
		} else {
			Logging.INSTANCE.warn("Email template with this id doesn't exists, no modifications");
		}
	}

	@Override
	public void deleteEmailTemplate(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		List<EmailTemplate> found = mongoTemplate.find(query, EmailTemplate.class);
		if (found.size() == 0) {
			Logging.INSTANCE.warn("Email template with this id doesn't exists, no deletions");
		}
		if (found.size() == 1) {
			mongoTemplate.remove(found.get(0));
			Logging.INSTANCE.info("Deleted Mongo Email Template: " + found.get(0).getName());
		} else {
			throw new BadRequestException("More than One Email Template was found.");
		}
	}

	@Override
	public Collection<EmailTemplate> getEmailTemplates() {
		return mongoTemplate.findAll(EmailTemplate.class);
	}

	@Override
	public Optional<EmailTemplate> getEmailTemplate(String id) {
		Query query = new Query(Criteria.where("id").is(id));
		List<EmailTemplate> found = mongoTemplate.find(query, EmailTemplate.class);
		if (found.size() == 1) {
			Optional<EmailTemplate> emailTemplate = Optional.of(found.get(0));
			return emailTemplate;
		}
		Optional<EmailTemplate> emptyEmailTemplate = Optional.empty();
		return emptyEmailTemplate;
	}
	
	@Override
	public Collection<EmailTemplate> getEmailTemplatesByName(String name) {
		Query query = new Query(Criteria.where("name").is(name));
		return mongoTemplate.find(query, EmailTemplate.class);
	}

	@Override
	public Collection<String> getEmailTemplateNames() {
		Collection<EmailTemplate> emailTemplates = mongoTemplate.findAll(EmailTemplate.class);
		Collection<String> emailTemplateNames = new HashSet<>();
		for(EmailTemplate emailTemplate: emailTemplates) {
			emailTemplateNames.add(emailTemplate.getId());
		}
		return emailTemplateNames;
	}
}
