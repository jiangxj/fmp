package com.luckymiao.wx.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luckymiao.base.BaseService;
import com.luckymiao.common.ResponseJson;
import com.luckymiao.common.utils.DateUtils;
import com.luckymiao.common.utils.SMSUtils;
import com.luckymiao.common.utils.StringUtils;
import com.luckymiao.wx.dao.T03_userDAO;
import com.luckymiao.wx.dao.T03_user_accountDAO;
import com.luckymiao.wx.dao.T03_user_withdrawDAO;
import com.luckymiao.wx.dto.T03_user;
import com.luckymiao.wx.dto.T03_user_account;
import com.luckymiao.wx.dto.T03_user_account_log;
import com.luckymiao.wx.dto.T03_user_project;
import com.luckymiao.wx.dto.T03_user_withdraw;
import com.luckymiao.wx.service.T03_userService;

@Service("t03_userServiceImpl")
public class T03_userServiceImpl extends BaseService implements T03_userService {
	@Autowired
	private T03_userDAO t03_userDAO;
	@Autowired
	private T03_user_accountDAO t03_user_accountDAO;
	@Autowired
	private T03_user_withdrawDAO t03_user_withdrawDAO;
	@Transactional
	@Override
	public ResponseJson registUser(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) throws Exception {
		ResponseJson result = new ResponseJson();
		String access_token = request.getParameter("access_token");
		try {
			HttpSession session = request.getSession();
			Map<String, String> tokenMap = (Map) session.getAttribute(t03User.getTelephone()
					+ "_ACCESS_TOKEN");
			if (org.apache.commons.lang.StringUtils.equals(access_token,
					tokenMap.get("ACCESS_TOKEN"))) {
				if (StringUtils.isMobilePhone(t03User.getTelephone())) {
					T03_user user = t03_userDAO.getT03_userByTelephone(t03User);
					if (user == null || "".equals(user.getTelephone())) {
						String uid = StringUtils.getDateNumberSequence();
						t03User.setUid(uid);
						t03User.setCreatedate(DateUtils.getCurrTime());
						t03User.setFlag("1");
						t03User.setRecommend_code(StringUtils.createRandom(true, 8));
						t03_userDAO.insertT03_user(t03User);
						
						T03_user_account t03_user_account = new T03_user_account();
						t03_user_account.setAid(StringUtils.getDateNumberSequence());
						t03_user_account.setBalance("0");
						t03_user_account.setCreatedate(DateUtils.getCurrTime());
						t03_user_account.setFlag("1");
						t03_user_account.setFreeze("0");
						t03_user_account.setUid(uid);
						t03_user_accountDAO.insertT03_user_account(t03_user_account);
						
						T03_user pUser = new T03_user();
						pUser.setRecommend_code(t03User.getCode());
						T03_user recUser = t03_userDAO.getT03_userByRecommend_code(pUser);
						if(recUser != null && !"".equals(recUser.getUid())){
							T03_user_account account = new T03_user_account();
							account.setUid(recUser.getUid());
							account.setBalance("0");
							
							T03_user_account recAccount = t03_user_accountDAO.getT03_user_accountByUid(account);
							BigDecimal b1 = new BigDecimal(recAccount.getBalance());
							BigDecimal b2 = new BigDecimal("0");
							String balance = b1.add(b2).toString();
							
							T03_user_account_log log = new T03_user_account_log();
							log.setLid(StringUtils.getDateNumberSequence());
							log.setAid(recAccount.getAid());
							log.setCoin("0");
							log.setCreatedate(DateUtils.getCurrTime());
							log.setType("1");//增加
							log.setBusinesstype("1");//邀请赠送
							log.setBalance(balance);
							t03_user_accountDAO.insertT03_user_account_log(log);
							
							t03_user_accountDAO.modifyT03_user_accountBalanceForAddByUid(account);
						}
						
						result.setCode("0");
						result.setCodemsg("注册成功!");
						session.removeAttribute(t03User.getTelephone()
								+ "_AUTHCODE");
						session.removeAttribute(t03User.getTelephone()
								+ "_ACCESS_TOKEN");
					} else {
						result.setCode("1");
						result.setCodemsg("用户已注册，不能重复注册！");
					}
				} else {
					result.setCode("2");
					result.setCodemsg("非法手机号!");
				}
			} else {
				result.setCode("3");
				result.setCodemsg("非法注册!");
			}

		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@Override
	public ResponseJson loginUser(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		try {
			T03_user dbUser = t03_userDAO.getT03_userByTelephone(t03User);

			if (dbUser == null) {
				result.setCode("2");
				result.setCodemsg("用户没有注册！");
			} else {
				T03_user user = t03_userDAO
						.getT03_userByTelephoneAndPassword(t03User);
				if (user == null || "".equals(user.getTelephone())) {
					result.setCode("1");
					result.setCodemsg("用户名密码不正确！");
				} else {
					result.setCode("0");
					result.setCodemsg("登陆成功！");
				}
			}
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson getUserInfo(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		try {
			T03_user dbUser = t03_userDAO.getT03_userByTelephone(t03User);

			if (dbUser == null) {
				result.setCode("1");
				result.setCodemsg("未找此用户");
			} else {
				Map dataMap = new HashMap();
				T03_user user = new T03_user();
				user.setAlipay_account(dbUser.getAlipay_account());
				user.setTelephone(dbUser.getTelephone());
				user.setRecommend_code(dbUser.getRecommend_code());
				dataMap.put("user", user);
				result.setData(dataMap);
				result.setCode("0");
				result.setCodemsg("查询成功");
			}
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson verifyAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		String userCode = request.getParameter("checkcode");
		try {
			HttpSession session = request.getSession();
			Map<String, Long> codeMap = (Map) session.getAttribute(t03User.getTelephone()
					+ "_AUTHCODE");
			if (codeMap != null
					&& (System.currentTimeMillis() - codeMap.get("TIMESTAMP")) < 60000) {
				if (org.apache.commons.lang.StringUtils.equals(userCode, String
						.valueOf(codeMap.get("AUTHCODE")))) {
					result.setCode("0");
					result.setCodemsg("验证通过");
					Map dataMap = new HashMap();
					String access_token = StringUtils.getUUID();
					dataMap.put("access_token", access_token);

					result.setData(dataMap);

					Map tokenMap = new HashMap();
					tokenMap.put("ACCESS_TOKEN", access_token);
					tokenMap.put("TELEPHONE", t03User.getTelephone());
					tokenMap.put("TIMESTAMP", String.valueOf(System
							.currentTimeMillis()));
					session.setAttribute(t03User.getTelephone()
							+ "_ACCESS_TOKEN", tokenMap);
				} else {
					result.setCode("1");
					result.setCodemsg("验证失败");
				}

			} else {
				result.setCode("2");
				result.setCodemsg("验证失败");
			}
			session.removeAttribute(t03User.getTelephone() + "_AUTHCODE");
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson modifyAlipayAccount(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		String access_token = request.getParameter("access_token");
		try {
			HttpSession session = request.getSession();
			Map<String, String> tokenMap = (Map) session.getAttribute(t03User
					.getTelephone()
					+ "_ACCESS_TOKEN");
			if (tokenMap != null
					&& (System.currentTimeMillis() - Long.parseLong(tokenMap
							.get("TIMESTAMP"))) < 180000
					&& org.apache.commons.lang.StringUtils.equals(access_token,
							tokenMap.get("ACCESS_TOKEN"))) {
				t03_userDAO.modifyT03_userAlipay_accountByTelephone(t03User);
				result.setCode("0");
				result.setCodemsg("修改成功");
			} else {
				result.setCode("1");
				result.setCodemsg("超时");
			}
			session.removeAttribute(t03User.getTelephone() + "_ACCESS_TOKEN");
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson sendAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		String telephone = request.getParameter("telephone");
		try {
			if(StringUtils.isMobilePhone(telephone)){
				HttpSession session = request.getSession();
				String authcode = StringUtils.createRandom(true, 4);
				SMSUtils.sendAuthCodeMessage(telephone, authcode);
				Map codeMap = new HashMap();
				codeMap.put("AUTHCODE", authcode);
				codeMap.put("TIMESTAMP", System.currentTimeMillis());
				session.setAttribute(t03User.getTelephone() + "_AUTHCODE", codeMap);
				result.setCode("0");
				result.setCodemsg("发送成功");
			}else{
				result.setCode("1");
				result.setCodemsg("发送失败，请使用正确的手机号");
			}
			
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson modifyUserTelephone(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		String access_token = request.getParameter("access_token");
		try {
			HttpSession session = request.getSession();
			Map<String, String> tokenMap = (Map) session.getAttribute(t03User
					.getTelephone()
					+ "_ACCESS_TOKEN");
			if (tokenMap != null
					&& (System.currentTimeMillis() - Long.parseLong(tokenMap
							.get("TIMESTAMP"))) < 180000
					&& org.apache.commons.lang.StringUtils.equals(access_token,
							tokenMap.get("ACCESS_TOKEN"))) {
				T03_user pUser = new T03_user();
				pUser.setNew_telephone(t03User.getTelephone());
				T03_user dbUser = t03_userDAO.getT03_userByTelephone(pUser);
				if (dbUser == null || "".equals(dbUser.getTelephone())) {
					t03_userDAO.modifyT03_userTelephoneByTelephone(t03User);
					result.setCode("0");
					result.setCodemsg("修改成功");
				} else {
					result.setCode("1");
					result.setCodemsg("该手机号已经被绑定，不能重复绑定");
				}

			} else {
				result.setCode("2");
				result.setCodemsg("超时");
			}
			session.removeAttribute(t03User.getTelephone() + "_ACCESS_TOKEN");
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson singleVerifyAuthCode(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		String userCode = request.getParameter("checkcode");
		try {
			HttpSession session = request.getSession();
			Map<String, Long> codeMap = (Map) session.getAttribute(t03User
					.getTelephone()
					+ "_AUTHCODE");
			if (codeMap != null
					&& (System.currentTimeMillis() - codeMap.get("TIMESTAMP")) < 60000) {
				if (org.apache.commons.lang.StringUtils.equals(userCode, String
						.valueOf(codeMap.get("AUTHCODE")))) {
					result.setCode("0");
					result.setCodemsg("验证通过");
					Map dataMap = new HashMap();
					String access_token = StringUtils.getUUID();
					dataMap.put("access_token", access_token);

					result.setData(dataMap);

					Map tokenMap = new HashMap();
					tokenMap.put("ACCESS_TOKEN", access_token);
					tokenMap.put("TELEPHONE", t03User.getTelephone());
					tokenMap.put("TIMESTAMP", String.valueOf(System
							.currentTimeMillis()));
					session.setAttribute(t03User.getTelephone()
							+ "_ACCESS_TOKEN", tokenMap);
				} else {
					result.setCode("1");
					result.setCodemsg("验证失败");
				}

			} else {
				result.setCode("2");
				result.setCodemsg("验证失败");
			}
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson getUserAccount(HttpServletRequest request,
			HttpServletResponse response, T03_user_account t03_user_account) {
		ResponseJson result = new ResponseJson();
		try {
			T03_user_account account = t03_userDAO.getT03_user_accountByTelephone(t03_user_account);
			if(account == null || org.apache.commons.lang.StringUtils.isBlank(account.getTelephone())){
				result.setCode("1");
				result.setCodemsg("查询失败");
			}else{
				T03_user_account_log log = new T03_user_account_log();
				log.setAid(account.getAid());
				log.setCreatedate(DateUtils.getCurrDate());
				List<T03_user_account_log> logList = t03_userDAO.getT03_user_account_logByAidShortDate(log);
				BigDecimal total = new BigDecimal("0");
				for (T03_user_account_log t03UserAccountLog : logList) {
						total = total.add(new BigDecimal(t03UserAccountLog.getCoin()));
				}
				Map dataMap = new HashMap();
				BigDecimal b1 = new BigDecimal(account.getBalance());
				BigDecimal b2 = new BigDecimal(account.getFreeze());
				dataMap.put("balance", b1.subtract(b2).toString());
				dataMap.put("today_income", total.toString());
				if("".equals(StringUtils.null2String(account.getAlipay_account()))){
					dataMap.put("alipay_account", "0");
				}else{
					dataMap.put("alipay_account", "1");
				}
				result.setData(dataMap);
				result.setCode("0");
				result.setCodemsg("查询成功");
			}
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson getUserProject(HttpServletRequest request,
			HttpServletResponse response, T03_user_project t03UserProject) {
		ResponseJson result = new ResponseJson();
		try {
			List<T03_user_project> projectList = t03_userDAO.getT03_user_projectByTelephone(t03UserProject);
			if(projectList == null || projectList.isEmpty()){
				result.setCode("1");
				result.setCodemsg("没有数据");
			}else{
				List unpassList = new ArrayList();
				List passList = new ArrayList();
				for (T03_user_project t03UserProject2 : projectList) {
					if("3".equals(t03UserProject2.getStatus())){
						passList.add(t03UserProject2);
					}else{
						unpassList.add(t03UserProject2);
					}
				}
				Map dataMap = new HashMap();
				dataMap.put("unpass", unpassList);
				dataMap.put("pass", passList);
				result.setData(dataMap);
				result.setCode("0");
				result.setCodemsg("查询成功");
			}
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}
	@Transactional
	@Override
	public ResponseJson peformUserWithdraw(HttpServletRequest request,
			HttpServletResponse response, T03_user_withdraw t03UserWithdraw) throws Exception{
		ResponseJson result = new ResponseJson();
		String access_token = request.getParameter("access_token");
		HttpSession session = request.getSession();
		Map<String, String> tokenMap = (Map) session.getAttribute(t03UserWithdraw.getTelephone()
				+ "_ACCESS_TOKEN");
		if (tokenMap != null
				&& (System.currentTimeMillis() - Long.parseLong(tokenMap
						.get("TIMESTAMP"))) < 180000
				&& org.apache.commons.lang.StringUtils.equals(access_token,
						tokenMap.get("ACCESS_TOKEN"))) {
			if(StringUtils.isNumberStr(t03UserWithdraw.getCoin())){
				BigDecimal coinBd = new BigDecimal(t03UserWithdraw.getCoin());
				BigDecimal s = new BigDecimal("8000");
				if(coinBd.subtract(s).toString().startsWith("-")){
					result.setCode("4");
					result.setCodemsg("最低提现金币数不能小于8000");
				}else{
					T03_user_account account = new T03_user_account();
					account.setTelephone(t03UserWithdraw.getTelephone());
					T03_user_account accountDb = t03_userDAO.getT03_user_accountByTelephone(account);
					BigDecimal b1 = new BigDecimal(accountDb.getBalance());
					BigDecimal b2 = new BigDecimal(accountDb.getFreeze());
					BigDecimal b3 = new BigDecimal(t03UserWithdraw.getCoin());
					String resultBalance = b1.subtract(b2).subtract(b3).toString();
					if(resultBalance.startsWith("-")){
						result.setCode("3");
						result.setCodemsg("余额不足");
					}else{
						T03_user t03User = new T03_user();
						t03User.setTelephone(t03UserWithdraw.getTelephone());
						T03_user userDb = t03_userDAO.getT03_userByTelephone(t03User);
						
						T03_user_withdraw withdraw = new T03_user_withdraw();
						withdraw.setWid(StringUtils.getDateNumberSequence());
						withdraw.setUid(userDb.getUid());
						withdraw.setCoin(t03UserWithdraw.getCoin());
						withdraw.setCreatedate(DateUtils.getCurrTime());
						withdraw.setStatus("1");
						withdraw.setBalance(resultBalance);
						
						t03_user_withdrawDAO.insertT03_user_withdraw(withdraw);
						//冻结资金
						T03_user_account accountp = new T03_user_account();
						accountp.setAid(accountDb.getAid());
						accountp.setFreeze(t03UserWithdraw.getCoin());
						t03_user_accountDAO.modifyT03_user_accountAddFreezeByAid(accountp);
						
						result.setCode("0");
						result.setCodemsg("修改成功");
					}
				}
			}else{
				result.setCode("2");
				result.setCodemsg("参数错误");
			}
		} else {
			result.setCode("1");
			result.setCodemsg("超时");
		}
		session.removeAttribute(t03UserWithdraw.getTelephone() + "_ACCESS_TOKEN");
		return result;
	}

	@Override
	public ResponseJson getUserWithdrawList(HttpServletRequest request,
			HttpServletResponse response, T03_user_withdraw t03UserWithdraw) {
		ResponseJson result = new ResponseJson();
		Map dataMap = new HashMap();
		try {
			List list = t03_user_withdrawDAO.getT03_user_withdrawListByTelephone(t03UserWithdraw);
			dataMap.put("list", list);
			result.setData(dataMap);
			result.setCode("0");
			result.setCodemsg("查询成功");
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson modifyUserPassword(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		String access_token = request.getParameter("access_token");
		try {
			HttpSession session = request.getSession();
			Map<String, String> tokenMap = (Map) session.getAttribute(t03User
					.getTelephone()
					+ "_ACCESS_TOKEN");
			if (tokenMap != null
					&& (System.currentTimeMillis() - Long.parseLong(tokenMap
							.get("TIMESTAMP"))) < 180000
					&& org.apache.commons.lang.StringUtils.equals(access_token,
							tokenMap.get("ACCESS_TOKEN"))) {
				T03_user pUser = new T03_user();
				pUser.setNew_telephone(t03User.getTelephone());
				T03_user dbUser = t03_userDAO.getT03_userByTelephone(pUser);
				if (dbUser == null || "".equals(dbUser.getTelephone())) {
					t03_userDAO.modifyT03_userPasswordByTelephone(t03User);
					result.setCode("0");
					result.setCodemsg("修改成功");
				} else {
					result.setCode("1");
					result.setCodemsg("该手机号已经被绑定，不能重复绑定");
				}

			} else {
				result.setCode("2");
				result.setCodemsg("超时");
			}
			session.removeAttribute(t03User.getTelephone() + "_ACCESS_TOKEN");
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResponseJson getUserInvitationList(HttpServletRequest request,
			HttpServletResponse response, T03_user t03User) {
		ResponseJson result = new ResponseJson();
		Map dataMap = new HashMap();
		try {
			int page = Integer.parseInt(StringUtils.null2One(request.getParameter("page")));
			List<T03_user> list = t03_userDAO.getT03_userListByCodeForInvitation(t03User);
			for (T03_user user : list) {
				String temp = user.getTelephone();
				user.setTelephone(temp.substring(0,3)+"***"+temp.substring(8));
			}
			dataMap.put("invitation_list", list);
			dataMap.put("pageInfo", this.getPageParameter("getT03_userListByCodeForInvitation"));
			result.setData(dataMap);
			result.setCode("0");
			result.setCodemsg("成功！");
		} catch (Exception e) {
			result.setStatusError();
			e.printStackTrace();
		}
		return result;
	}

}
