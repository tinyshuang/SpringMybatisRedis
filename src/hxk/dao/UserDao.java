package hxk.dao;

import hxk.model.User;


/**
 * 
 * @author hxk
 * @description dao层直接声明接口,在mapper实现
 *2015年8月24日  下午3:00:10
 */
public interface UserDao {
	public void save(User user);
	public void update(User user);
	public User find(String id);
	public void delete(String id);
}
