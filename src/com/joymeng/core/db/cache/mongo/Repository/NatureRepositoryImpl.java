package com.joymeng.core.db.cache.mongo.Repository;

import java.util.List;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.joymeng.core.db.cache.mongo.domain.Tree;
import com.mongodb.WriteResult;

public class NatureRepositoryImpl implements Repository {

	MongoTemplate mongoTemplate;

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * Get all trees.
	 */
	public List<Tree> getAllTrees() {
		return mongoTemplate.findAll(Tree.class);
	}

	/**
	 * Saves a {@link Tree}.
	 */
	public void saveTree(Tree tree) {
		mongoTemplate.insert(tree);
	}

	/**
	 * Gets a {@link Tree} for a particular id.
	 */
	public Tree getTree(String id) {
		return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)),
				Tree.class);
	}

	/**
	 * Updates a {@link Tree} name for a particular id.
	 */
	public WriteResult updateTree(String id, String name) {
		return mongoTemplate.updateFirst(
				new Query(Criteria.where("id").is(id)),
				Update.update("name", name), Tree.class);
	}

	/**
	 * Delete a {@link Tree} for a particular id.
	 */
	public void deleteTree(String id) {
		mongoTemplate
				.remove(new Query(Criteria.where("id").is(id)), Tree.class);
	}

	/**
	 * Create a {@link Tree} collection if the collection does not already
	 * exists
	 */
	public void createCollection() {
		if (!mongoTemplate.collectionExists(Tree.class)) {
			mongoTemplate.createCollection(Tree.class);
		}
	}

	/**
	 * Drops the {@link Tree} collection if the collection does already exists
	 */
	public void dropCollection() {
		if (mongoTemplate.collectionExists(Tree.class)) {
			mongoTemplate.dropCollection(Tree.class);
		}
	}
}
