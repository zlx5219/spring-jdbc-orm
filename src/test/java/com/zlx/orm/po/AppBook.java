package com.zlx.orm.po;

import java.util.Date;

import com.zlx.orm.BaseEntity;
import com.zlx.orm.Constants;
import com.zlx.orm.annotation.Table;
import com.zlx.orm.annotation.TableColumn;

@Table(name = "app_book")
public class AppBook extends BaseEntity
{
	private static final long serialVersionUID = -1786074284782916886L;
	@TableColumn(value = "com_id", isKey = true)
	private String comId;
	@TableColumn("com_name")
	private String comName;
	@TableColumn("phase_id")
	private int phaseId = Constants.ENTITY_NULLITY_DEFAULT;
	@TableColumn("grade_level_id")
	private int gradeLevelId = Constants.ENTITY_NULLITY_DEFAULT;
	@TableColumn("subject_id")
	private int subjectId = Constants.ENTITY_NULLITY_DEFAULT;
	@TableColumn("publisher_id")
	private int publisherId = Constants.ENTITY_NULLITY_DEFAULT;
	@TableColumn("fascicule_id")
	private int fasciculeId = Constants.ENTITY_NULLITY_DEFAULT;
	@TableColumn("book_edtion")
	private String bookEdtion;//'版次（原数据）'
	@TableColumn
	private String source; //书的来源 U3  SYS
	@TableColumn
	private String code; //教育信息 学段学科年级出版社
	@TableColumn(value = "s_create_time", defaultOrder = true, ascending = true)
	private Date createTime;
	@TableColumn("s_state_time")
	private Date stateTime;
	@TableColumn("s_state")
	private int state = Constants.ENTITY_NULLITY_DEFAULT;
	@TableColumn("s_state_operator")
	private int stateOperator = Constants.ENTITY_NULLITY_DEFAULT;
	@TableColumn("s_state_operator_name")
	private String stateOperatorName;

	public String getComId()
	{
		return comId;
	}
	public void setComId(String comId)
	{
		this.comId = comId;
	}
	public String getComName()
	{
		return comName;
	}
	public void setComName(String comName)
	{
		this.comName = comName;
	}
	public int getPhaseId()
	{
		return phaseId;
	}
	public void setPhaseId(int phaseId)
	{
		this.phaseId = phaseId;
	}
	public int getGradeLevelId()
	{
		return gradeLevelId;
	}
	public void setGradeLevelId(int gradeLevelId)
	{
		this.gradeLevelId = gradeLevelId;
	}
	public int getSubjectId()
	{
		return subjectId;
	}
	public void setSubjectId(int subjectId)
	{
		this.subjectId = subjectId;
	}
	public int getPublisherId()
	{
		return publisherId;
	}
	public void setPublisherId(int publisherId)
	{
		this.publisherId = publisherId;
	}
	public int getFasciculeId()
	{
		return fasciculeId;
	}
	public void setFasciculeId(int fasciculeId)
	{
		this.fasciculeId = fasciculeId;
	}
	public String getBookEdtion()
	{
		return bookEdtion;
	}
	public void setBookEdtion(String bookEdtion)
	{
		this.bookEdtion = bookEdtion;
	}
	public String getSource()
	{
		return source;
	}
	public void setSource(String source)
	{
		this.source = source;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public Date getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}
	public Date getStateTime()
	{
		return stateTime;
	}
	public void setStateTime(Date stateTime)
	{
		this.stateTime = stateTime;
	}
	public int getState()
	{
		return state;
	}
	public void setState(int state)
	{
		this.state = state;
	}
	public int getStateOperator()
	{
		return stateOperator;
	}
	public void setStateOperator(int stateOperator)
	{
		this.stateOperator = stateOperator;
	}
	public String getStateOperatorName()
	{
		return stateOperatorName;
	}
	public void setStateOperatorName(String stateOperatorName)
	{
		this.stateOperatorName = stateOperatorName;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("{\"comId\":\"");
		builder.append(comId);
		builder.append("\", comName\":\"");
		builder.append(comName);
		builder.append("\", phaseId\":\"");
		builder.append(phaseId);
		builder.append("\", gradeLevelId\":\"");
		builder.append(gradeLevelId);
		builder.append("\", subjectId\":\"");
		builder.append(subjectId);
		builder.append("\", publisherId\":\"");
		builder.append(publisherId);
		builder.append("\", fasciculeId\":\"");
		builder.append(fasciculeId);
		builder.append("\", bookEdtion\":\"");
		builder.append(bookEdtion);
		builder.append("\", source\":\"");
		builder.append(source);
		builder.append("\", code\":\"");
		builder.append(code);
		builder.append("\", createTime\":\"");
		builder.append(createTime);
		builder.append("\", stateTime\":\"");
		builder.append(stateTime);
		builder.append("\", state\":\"");
		builder.append(state);
		builder.append("\", stateOperator\":\"");
		builder.append(stateOperator);
		builder.append("\", stateOperatorName\":\"");
		builder.append(stateOperatorName);
		builder.append("}");
		return builder.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bookEdtion == null) ? 0 : bookEdtion.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((comId == null) ? 0 : comId.hashCode());
		result = prime * result + ((comName == null) ? 0 : comName.hashCode());
		result = prime * result
				+ ((createTime == null) ? 0 : createTime.hashCode());
		result = prime * result + fasciculeId;
		result = prime * result + gradeLevelId;
		result = prime * result + phaseId;
		result = prime * result + publisherId;
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + state;
		result = prime * result + stateOperator;
		result = prime
				* result
				+ ((stateOperatorName == null) ? 0 : stateOperatorName
						.hashCode());
		result = prime * result
				+ ((stateTime == null) ? 0 : stateTime.hashCode());
		result = prime * result + subjectId;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppBook other = (AppBook) obj;
		if (bookEdtion == null)
		{
			if (other.bookEdtion != null)
				return false;
		}
		else if (!bookEdtion.equals(other.bookEdtion))
			return false;
		if (code == null)
		{
			if (other.code != null)
				return false;
		}
		else if (!code.equals(other.code))
			return false;
		if (comId == null)
		{
			if (other.comId != null)
				return false;
		}
		else if (!comId.equals(other.comId))
			return false;
		if (comName == null)
		{
			if (other.comName != null)
				return false;
		}
		else if (!comName.equals(other.comName))
			return false;
		if (createTime == null)
		{
			if (other.createTime != null)
				return false;
		}
		else if (!createTime.equals(other.createTime))
			return false;
		if (fasciculeId != other.fasciculeId)
			return false;
		if (gradeLevelId != other.gradeLevelId)
			return false;
		if (phaseId != other.phaseId)
			return false;
		if (publisherId != other.publisherId)
			return false;
		if (source == null)
		{
			if (other.source != null)
				return false;
		}
		else if (!source.equals(other.source))
			return false;
		if (state != other.state)
			return false;
		if (stateOperator != other.stateOperator)
			return false;
		if (stateOperatorName == null)
		{
			if (other.stateOperatorName != null)
				return false;
		}
		else if (!stateOperatorName.equals(other.stateOperatorName))
			return false;
		if (stateTime == null)
		{
			if (other.stateTime != null)
				return false;
		}
		else if (!stateTime.equals(other.stateTime))
			return false;
		if (subjectId != other.subjectId)
			return false;
		return true;
	}
	
}
