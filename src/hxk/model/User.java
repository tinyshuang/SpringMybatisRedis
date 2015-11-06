package hxk.model;

import java.io.Serializable;

/**
 * @author hxk
 * @description 一个普通的java类
 *2015-8-21  下午3:58:55
 */
public class User implements Serializable{
    private static final long serialVersionUID = -1013508270108910981L;
    
    private String name;
    private String pwd;
    private String role;
    
    public User() {}


    public User(String name, String pwd, String role) {
	super();
	this.name = name;
	this.pwd = pwd;
	this.role = role;
    }
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    
    
    
    
}
