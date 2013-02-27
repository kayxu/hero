package com.joymeng.core.db.cache.mongo;

import java.util.List;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.joymeng.core.db.cache.mongo.Repository.NatureRepositoryImpl;
import com.joymeng.core.db.cache.mongo.Repository.Repository;
import com.joymeng.core.db.cache.mongo.domain.Tree;
import com.joymeng.game.cache.domain.Log;
import com.joymeng.game.cache.respository.LogImpl;
import com.joymeng.game.cache.respository.LogInterface;
import com.joymeng.game.domain.world.World;

/**
 * spring mongodb 示例程序 ，详细参考 http://tech.it168.com/a2012/0816/1386/000001386020_all.shtml 
 * @author admin
 *
 */
public class MongoTest {
//	private static final MongoTest instance = new MongoTest();
//	public static final MongoTest getInstance() {
//		return instance;
//	}
//	LogInterface _log;
//	public MongoTest(){
//		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
//				"conf/mongo-config.xml");
//		 _log = context.getBean(LogImpl.class);
//	}
//	public LogInterface get_log() {
//		return _log;
//	}
//	public void set_log(LogInterface _log) {
//		this._log = _log;
//	}
//	public static void main(String[] args) {
////		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
////				"mongo-config.xml");
////		LogInterface _log = context.getBean(LogImpl.class);
//		MongoServer.getInstance().getGoodsCache().getBuyRecordByGid(85);
//		MongoServer.getInstance().getShopCache().getTopSell(8);
//		//增加一个测试数据
////		Log log=new Log();
////		log.setMessage("TEST");
////		_log.save(log);
////		_log.script();
////		List<Log> list=_log.getAllLogs();
////		for(Log _l:list){
////			System.out.println("=="+_l.toString());
////		}
////		_log.getAllLogs2();
//		System.exit(0);
//	}
	public static void main(String[] args) {
//
		ConfigurableApplicationContext context = new ClassPathXmlApplicationContext(
				"conf/mongo-test.xml");

		// Repository repository =
		// (Repository)context.getBean("natureRepository");

		Repository repository = context.getBean(NatureRepositoryImpl.class);

		// cleanup collection before insertion
		repository.dropCollection();

		// create collection
		repository.createCollection();

		repository.saveTree(new Tree("1", "Apple Tree", 10));

		System.out.println("1. " + repository.getAllTrees());

		repository.saveTree(new Tree("2", "Orange Tree", 3));

		System.out.println("2. " + repository.getAllTrees());

		System.out.println("Tree with id 1" + repository.getTree("1"));

		repository.updateTree("1", "Peach Tree");

		System.out.println("3. " + repository.getAllTrees());

		repository.deleteTree("2");

		System.out.println("4. " + repository.getAllTrees());
	}
}
