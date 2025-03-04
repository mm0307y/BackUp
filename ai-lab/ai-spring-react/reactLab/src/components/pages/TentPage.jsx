import axios from 'axios'
import React, { useEffect, useState } from 'react'

const TentPage = () => {
    console.log("시작")
    //정해진 시간동안에는 이벤트가 출력되다가 시간이 지나면 화면에서 제거하기
    const [message, setMessage] = useState(true)
    const [tents, setTents] = useState([]) //배열
    const [tent, setTent] = useState({ //객체
        "id":4,
        "title":"텐트-4",
        "content":"2-3인용 텐트",
        "price":30000,
        "img":"tent4.jpg"
    })
    useEffect(()=> {
        //컴포넌트가 mount, update시 여기 코드 실행됨
        //마운트란 컴포넌트(TentPage)가 최초로 생성되어 브라우저에 DOM삽입되는 과정을 말함.
        console.log("useEffect")
        const timer = setTimeout(() => {setMessage(false)}, 3000)
        getTentList() //컴포넌트가 마운트 될때 필요한 데이터를 불러오는 역할을 수행함.
        return () => {//일명 cleanup function 이라고 부름
            //기존 코드를 치우는 용도로 사용됨.
            //mount시에는 실행이 안됨, unmount실행됨
            console.log("cleanup function 전에")
            clearTimeout(timer)
        }
    },[]) //의존성 배열이 빈경우 업데이트 시에는 실행되지 않음.
    const getTentList = () => {
        axios.get("/tent.json").then(res => {
            console.log(res.data)
            setTents(res.data)
            console.log(tents)
        })
    }
    return (
        <>
            {
                message === true
                ?
                <div>
                    3초 이내 구매시 할인 행사 중....
                    <ul>
                        {tents.map((tent, index) => (
                            <li key={index}>{`상품명:${tent.title}, 가격 : ${tent.price}`}</li>
                        ))}
                    </ul>
                    <button>장바구니담기</button>
                </div>
                : null
            }
            <hr />
            이벤트 종료 후 <br />
            {`${tent.id}, ${tent['content']}, ${tent.content}`}
        </>
    )
}

export default TentPage