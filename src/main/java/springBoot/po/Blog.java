package springBoot.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity			//使用的是jpa所以需要加入此标记
@Table(name = "t_blog")	//指定数据库里面的table
public class Blog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String title;		//标题
	private String content;		//内容
	private String firstPicture;	//首图
	private String flag;		//标记
	private Integer views;		//浏览次数
	private boolean appreciation;	//赞赏开启
	private boolean shareStatement;		//版权开启
	private boolean commentabled;		//评论开启
	private boolean recommend;		//是否推荐
	private boolean pubished;		//发布
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;		//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;		//更新时间
	
	@ManyToOne
	private Type type;
	
	@ManyToMany(cascade = CascadeType.PERSIST)	//新增博客连同tag一起新增就要加括号里面的东西 
	private List<Tag> tags = new ArrayList<>();
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();
	
	
	
	public Blog() {
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getFirstPicture() {
		return firstPicture;
	}


	public void setFirstPicture(String firstPicture) {
		this.firstPicture = firstPicture;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}


	public Integer getViews() {
		return views;
	}


	public void setViews(Integer views) {
		this.views = views;
	}


	public boolean isAppreciation() {
		return appreciation;
	}


	public void setAppreciation(boolean appreciation) {
		this.appreciation = appreciation;
	}


	public boolean isShareStatement() {
		return shareStatement;
	}


	public void setShareStatement(boolean shareStatement) {
		this.shareStatement = shareStatement;
	}


	public boolean isCommentabled() {
		return commentabled;
	}


	public void setCommentabled(boolean commentabled) {
		this.commentabled = commentabled;
	}


	public boolean isRecommend() {
		return recommend;
	}


	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}


	public boolean isPubished() {
		return pubished;
	}


	public void setPubished(boolean pubished) {
		this.pubished = pubished;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Date getUpdateTime() {
		return updateTime;
	}


	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	

	public List<Tag> getTags() {
		return tags;
	}


	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}


	@Override
	public String toString() {
		return "blog [id=" + id + ", title=" + title + ", content=" + content + ", firstPicture=" + firstPicture
				+ ", flag=" + flag + ", views=" + views + ", appreciation=" + appreciation + ", shareStatement="
				+ shareStatement + ", commentabled=" + commentabled + ", recommend=" + recommend + ", pubished="
				+ pubished + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	
	
}
