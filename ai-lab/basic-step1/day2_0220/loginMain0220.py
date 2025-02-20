# 아이디와 비밀번호 입력
# DB서버에서 관리되는 아이디와 비밀번호
# 당신의 아이디를 입력하세요.
# 당신의 비빌번호를 입력하세요.

import loginAction0220 as la

db_name = "키위나무"

user_id = input("당신의 아이디를 입력하세요. \n")
user_pw = input("당신의 비밀번호를 입력하세요. \n")
# 로그인 처리는 loginAction0220.py에서 처리한다.
result = la.login(user_id, user_pw)
print(result)

if result == 1:
  print(f"{db_name} 님 환영힙니다.")
elif result == 2:
  print("비밀번호가 틀립니다.")
elif result == -1:
  print("아이디가 존재하지 않습니다.")

# XXX님 환영합니다.