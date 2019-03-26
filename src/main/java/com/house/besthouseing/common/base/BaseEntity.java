package com.house.besthouseing.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Date;

@Configuration
public class BaseEntity<T> implements Serializable {
	
	private static final long serialVersionUID = -8790917399634620779L;

	/** 创建者 */
	private Integer createBy = 0;

	/** 创建时间 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;

	/** 更新者 */
	private transient Integer updateBy;

	/** 更新时间 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private transient Date updateTime;

	/** 备注 */
	private String remark = "";

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
