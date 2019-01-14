package com.bluehonour.web.service.impl;

import com.bluehonour.web.dao.AbnormalAccountDao;
import com.bluehonour.web.dao.impl.AbnormalAccountDaoImpl;
import com.bluehonour.web.service.AbnormalAccountServiceDao;

public class AbnormalAccountServiceDaoImpl implements AbnormalAccountServiceDao {

	@Override
	public int getAccountNum() {
		AbnormalAccountDao dao = new AbnormalAccountDaoImpl();
		return dao.getAccountNum();
	}

}
