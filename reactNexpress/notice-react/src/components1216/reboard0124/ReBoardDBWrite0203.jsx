import React, { useCallback, useRef, useState } from 'react'
import Header1216 from '../include1216/Header1216'
import { BButton, ContainerDiv, FormDiv, HeaderDiv } from '../../styles1226/FormStyles'
import { useNavigate } from 'react-router'
import { reBoardInsertDB } from '../../service1216/dbLogic1218'
import ReQuillEditor0203 from './ReQuillEditor0203'
import Footer1216 from '../include1216/Footer1216'

const ReBoardDBWrite0203 = () => {
  const navigate = useNavigate()
  const quillRef = useRef()

  // 이 때 마다 handleTitle함수가 매번 새로 만들어진다. - 비효율적이다.
  // 제목 작성이 끝났을 때 딱 한 번만 함수가 새로 만들어지면 된다. - 함수를 메모이제이션한다.
  const [title, setTitle] = useState('') // 제목을 쓸 때 마다 - 키보드가 눌렸다가 떼어질 때 마다 상태가 변한다.
  const [content, setContent] = useState('')
  const [email, setEmail] = useState(() => {
    // localStorage에서 초기값을 가져온다.
    return localStorage.getItem("email") || "";
  })

  const handleTitle = useCallback((e) => {
    setTitle(e)
  }, [])

  const handleContent = useCallback((e) => {
    setContent(e) // 훅 상태값이 변한다. -> 변할 때 마다 ReBoardWrite()호출된다. -> 그 때마다 함수도 새로 생성된다.
  }, [])

  const handleEmail = useCallback((e) => {
    setEmail(e)
  }, [])

  // 글쓰기 요청시 호출될 함수 구현
  const boardInsert = async () => {
    if (title.trim() === "|| content.rim()===") {
      alert("게시글이 작성되지 않았습니다.")
      return;
    }

    const board = {
      b_title: title,
      email: email,
      b_content: content,
    }

    const res = await reBoardInsertDB(board)
    if (!res.data) alert("게시판 글쓰기에 실패 하였습니다.")
    navigate('/reboard?page=1')
  } // end of boardInsert

  return (
    <>
      <Header1216 />
      <ContainerDiv>
        <HeaderDiv>
          <h3>게시글 작성</h3>
        </HeaderDiv>
        <FormDiv>
          <div style={{ width: "100%", maxWidth: "2000px" }}>
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px' }}>
              <h2>제목</h2>
              <div style={{ display: 'flex' }}>
                <BButton style={{ marginLeft: '10px' }} onClick={boardInsert}>글쓰기</BButton>
              </div>
            </div>
            <input id="dataset-title" type="text" maxLength="50" placeholder="제목을 입력하세요."
              style={{ width: "100%", height: '40px', border: '1px solid lightGray' }} onChange={(e) => { handleTitle(e.target.value) }} />
            <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '5px', marginTop: '5px' }}>
              <h2>작성자</h2>
            </div>
            <input id="dataset-writer" type="text" maxLength="20" placeholder="작성자를 입력하세요."
              style={{ width: "200px", height: '40px', border: '1px solid lightGray' }} value={email} onChange={(e) => { handleEmail(e.target.value) }} />
            <hr style={{ margin: '10px 0px 10px 0px' }} />
            <h3>상세내용</h3>
            <ReQuillEditor0203 value={content} handleContent={handleContent} quillRef={quillRef} />
          </div>
        </FormDiv>
      </ContainerDiv>
      <Footer1216 />
    </>
  )
}

export default ReBoardDBWrite0203