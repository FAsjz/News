package com.feicui.news.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.UserManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.feicui.news.MainActivity;
import com.feicui.news.NoNetConnectionActivity;
import com.feicui.news.R;
import com.feicui.news.UserActivity;
import com.feicui.news.common.LogUtil;
import com.feicui.news.common.SharedPreferencesUtil;
import com.feicui.news.common.SystemUtil;
import com.feicui.news.common.TextUtil;
import com.feicui.news.common.ToastUtil;
import com.feicui.news.model.biz.parser.GsonParse;
import com.feicui.news.model.dao.LoginManager;
import com.feicui.news.model.dao.RegistManager;
import com.feicui.news.model.entity.BaseEntity;
import com.feicui.news.model.entity.Register;

public class FragmentRegister extends Fragment {
	private EditText email, nickName, password;
	private Button regist;
	private UserManager userMgr;
	private CheckBox cb;
	private String s1,s2,s3;
	private ProgressDialog dialog;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_register, null);
		email = (EditText) view.findViewById(R.id.editText_email);
		nickName = (EditText) view.findViewById(R.id.editText_name);
		password = (EditText) view.findViewById(R.id.editText_pwd);
		regist = (Button) view.findViewById(R.id.button_register);
		cb = (CheckBox) view.findViewById(R.id.checkBox1);
		// 注册按钮
		regist.setOnClickListener(gistlistener);

		return view;
	}

	OnClickListener gistlistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			s1 = email.getText().toString();
			s2 = nickName.getText().toString();
			s3 = password.getText().toString();
			LogUtil.d("233", s1 + "," + s2 + "," + s3);
			if (cb.isChecked()) {
				if (!TextUtil.isEmail(s1)) {
					ToastUtil.ToastShow(getActivity(), "请输入正确的邮箱格式");
					return;
				}
				if (s3.length() < 6 || s3.length() > 16) {
					ToastUtil.ToastShow(getActivity(), "密码长度错误");
					return;
				}
				if (s2.length() == 0) {
					ToastUtil.ToastShow(getActivity(), "名称不能为空");
					return;
				}
				if (SystemUtil.isNetConn(getActivity()) == true) {
					dialog = ProgressDialog.show(getActivity(), null, "正在注册...");
					RegistManager.loadRegist(getActivity(), listener,
							errorListener, s2, s1, s3);
				}else {
					startActivity(new Intent(getActivity(),NoNetConnectionActivity.class));
				}
				

			} else {
				ToastUtil.ToastShow(getActivity(), "请先点击我同意，再进行注册");
				return;
			}

		}

	};

	Listener<String> listener = new Listener<String>() {

		@Override
		public void onResponse(String response) {
			
			BaseEntity<Register> baseEntity = (BaseEntity<Register>) GsonParse.getRegist(response);
			dialog.dismiss();
			if (baseEntity.getStatus() == -1) {
				ToastUtil.ToastShow(getActivity(), baseEntity.getMessage());
			}
			Register register = baseEntity.getData();
			if (register == null) {
				return;
			}
			LogUtil.d("Register", register.toString());
			dialog.dismiss();
			switch (register.getResult()) {
			case 0:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				SharedPreferencesUtil.saveRegister(getActivity(), register);
				SharedPreferences shared = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
				Editor ed = shared.edit();
				ed.putString("username", s2);
				ed.putString("password", s3);
				ed.putString("token", register.getToken());
				ed.commit();
				Intent it = new Intent(getActivity(),UserActivity.class);
				it.putExtra("login", true);
				getActivity().startActivity(it);
				getActivity().finish();
				break;
			case -1:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				break;
			case -2:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				break;
			case -3:
				ToastUtil.ToastShow(getActivity(), register.getExplain());
				break;

			}

		}

	};
	ErrorListener errorListener = new ErrorListener() {

		@Override
		public void onErrorResponse(VolleyError error) {
			ToastUtil.ToastShow(getActivity(), "网络连接异常");
			LogUtil.d("Fragmentregister", "网络连接异常");
			dialog.dismiss();
		}

	};

}
