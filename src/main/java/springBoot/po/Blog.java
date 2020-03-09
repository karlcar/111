package springBoot.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity			//使用的是jpa所以需要加入此标记
@Table(name = "t_blog")	//指定数据库里面的table
public class Blog {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;		//标题
	
	@Basic(fetch = FetchType.LAZY) 
	@Lob
	private String content;		//内容
	
	private String firstPicture;	//首图
	private String flag;		//标记
	private Integer views;		//浏览次数
	private boolean appreciation;	//赞赏开启
	private boolean shareStatement;		//版权开启
	private boolean commentabled;		//评论开启
	private boolean recommend;		//是否推荐
	private boolean published;		//发布
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;		//创建时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;		//更新时间
	
	private String description;	    //描述
	
	@ManyToOne
	private Type type;
	
	@ManyToMany(cascade = CascadeType.PERSIST)	//新增博客连同tag一起新增就要加括号里面的东西 
	private List<Tag> tags = new ArrayList<>();
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy = "blog")
	private List<Comment> comments = new ArrayList<>();
	
	@Transient	
	private String tagIds;	//博客提交时候的选中的字符串的ids，这里的@Transient表示不会入库的
	
	
	
	public Blog() {
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
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


	public boolean isPublished() {
		return published;
	}


	public void setPublished(boolean published) {
		this.published = published;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTagIds() {
		return tagIds;
	}


	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}


	//初始化例如tagIds，将数组重新转化成字符串
	public void init() {
		this.tagIds = tagsToIds(this.getTags());
	}
	
	private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }
 
	@Override
	public String toString() {
		return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
	}
	
	
	
}
