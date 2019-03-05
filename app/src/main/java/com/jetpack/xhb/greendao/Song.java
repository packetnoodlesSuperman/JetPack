package com.jetpack.xhb.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Song {

    @Id(autoincrement = true)
    private Long id;

    private String songName;

    private Integer songId;

    private String songDesc;

    private String cover;

    private Integer singerCode;

    private String singerName;

    private String createTime;

    private String updateTime;

    @Generated(hash = 1036160165)
    public Song(Long id, String songName, Integer songId, String songDesc,
            String cover, Integer singerCode, String singerName, String createTime,
            String updateTime) {
        this.id = id;
        this.songName = songName;
        this.songId = songId;
        this.songDesc = songDesc;
        this.cover = cover;
        this.singerCode = singerCode;
        this.singerName = singerName;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    @Generated(hash = 87031450)
    public Song() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getSongId() {
        return this.songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getSongDesc() {
        return this.songDesc;
    }

    public void setSongDesc(String songDesc) {
        this.songDesc = songDesc;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getSingerCode() {
        return this.singerCode;
    }

    public void setSingerCode(Integer singerCode) {
        this.singerCode = singerCode;
    }

    public String getSingerName() {
        return this.singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
