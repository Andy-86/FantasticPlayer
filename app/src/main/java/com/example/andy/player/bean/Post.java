package com.example.andy.player.bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 帖子实体类
 * 注：实体类的结构在实际项目中应在一定程度上参照服务器后台对应表的数据结构来设计，这里我就不多BB了
 *
 * @author Aige
 * @since 2014/11/14
 */
public class Post {
    private String flag;//评论标识：系统随机生成
    private String createAt;//评论时间：系统生成
    private List<Comment> comments;//该帖子下的所有评论，按插入数据库的_id顺序排列保证时间先后的统一
    private int userPraises;//该帖子赞的人数、踩的人数和收藏的人数

    public int getUserPraises() {
        return userPraises;
    }

    public void setUserPraises(int userPraises) {
        this.userPraises = userPraises;
    }

    /**
     * 评论实体的构造函数
     * 两个成员变量均由系统赋值
     */
    public Post() {
        //生成随机标识，这个随机标识准确来说应该是服务端生成，这里就不麻烦了 = =
        flag = UUID.randomUUID().toString();

        //生成系统时间，这个数据创建时间也应该是服务端生成
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss", Locale.getDefault());
        this.createAt = format.format(date);
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


    /**
     * 帖子类型的枚举类
     */
    public enum Type {
        HOTTEST, NEWEST, NORMAL
    }
}
