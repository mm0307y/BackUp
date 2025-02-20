db_id = "kiwi"
db_pw = "123"

def login(user_id, user_pw): # 파이썬 함수 선언하기
  print("login 함수 호출 성공")
  state = 1001
  if db_id == user_id:
    state = 0
    # 아이디가 존재하면 비밀번호를 비교합니다.
    if db_pw == user_pw:
      state = 1 # 아이디도 존재하고 비밀번호도 일치한다.
    else:
      state = 2 # 비밀번호가 틀리다.
  
  else :
    state = -1
  return state