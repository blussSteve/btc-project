package com.btc.util.enums;

public enum PushMsgEnum {
	/**赎回申请通知*/
	M_1(1,"您的投资者中有“{num}”人申请从您这里赎回总额为“{sum}”的现值，由于您的可用资金不足导致投资者们赎回失败，故需要您卖出部分股票以满足投资者们的赎回需求，请予以配合，谢谢！"),
	/**乐享生活通知*/
	M_2(2,"上帝，您好！您{date}在乐享生活兑换{goodsName}支出{costGoldNum}航币，余额{curGoldNum}航币!"),
	/**截止日期以后的报名通知*/
	M_3(3,"小伙伴，你好，本季大赛已进入尾期，当前报名只能体验比赛和了解比赛详情，但不影响你下一季的排名及奖项竞争。"),
	M_4(4,"欢迎您参加NIC第二季季前赛！");
	
	private int code;
	private String desc;
	
	private PushMsgEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
  public static PushMsgEnum getByCode(int code) {
        for (PushMsgEnum enums : PushMsgEnum.values()) {
            if (enums.getCode()==code) {
                return enums;
            }
        }
        return null;
    }
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
