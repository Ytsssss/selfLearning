package com.self.leaning.demo.data.dao.common;

import com.mongodb.ReadPreference;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Mongo库访问模板
 *
 * @author liuhaoi
 */
public abstract class AbstractMongoRepository<T> {

    protected final Class<T> clazz;

    protected final String collectionName;

    protected final MongoTemplate template;

    public AbstractMongoRepository(Class<T> clazz, MongoTemplate template) {
        this.clazz = clazz;
        this.template = template;
        template.setReadPreference(ReadPreference.primaryPreferred());
        collectionName = template.getCollectionName(clazz);
    }
}

