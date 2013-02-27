package com.joymeng.core.db.cache.mongo.Repository;

import java.util.List;

import com.joymeng.core.db.cache.mongo.domain.Tree;
import com.mongodb.WriteResult;

public interface Repository {

	public List<Tree> getAllTrees();

	public void saveTree(Tree tree);

	public Tree getTree(String id);

	public WriteResult updateTree(String id, String name);

	public void deleteTree(String id);

	public void createCollection();

	public void dropCollection();
}
