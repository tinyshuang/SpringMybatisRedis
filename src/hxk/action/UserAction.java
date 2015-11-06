package hxk.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import hxk.dao.UserDao;
import hxk.model.User;

/**
 * @author hxk
 * @description 简单的Action的Demo..实现了增删改查..
 *2015年8月24日  下午1:51:15
 */
@Controller
@RequestMapping("/user/")
public class UserAction {
    @Resource
    private UserDao userDao;
    
    
    @RequestMapping("save")
    public @ResponseBody String save(){
	User user = new User("tinys", "123456", "0");
	userDao.save(user);
	return "save ok";
    }
    
    @RequestMapping("update")
    public @ResponseBody String update(){
	User user = new User("tinys", "123456", "1");
	userDao.update(user);
	return "update ok";
    }
    
    @RequestMapping("delete")
    public @ResponseBody String delete(){
	userDao.delete("tinys");
	return "delete ok";
    }
    
    @RequestMapping("find")
    public @ResponseBody String find(){
	User user = userDao.find("tinys");
	return user.getName() + user.getPwd() + user.getRole();
    }
    
    
}
