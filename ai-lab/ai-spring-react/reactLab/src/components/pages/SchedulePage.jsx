import React, { Fragment, useEffect, useState } from 'react'
import Header from '../include/Header'
import Footer from '../include/Footer'
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import axios from 'axios'
//jsx는 함수다. 함수인데 사용자 정의 태그로 사용이 가능하다.
//함수인데 어떻게 화면을 그려주나요 -> DOM Tree그린다.
//return -> Fragment -> html태그 쓴다. ->js
//jsx -> 태그랑 자바스크립트 섞어쓰기 -> 단 {} 쓰고 그 안에 써라.
//함수안에 함수를 여러개 선언할 수 있다.
//useState가 바뀌면 SchedulePage함수가 재 호출된다.
//return에 있는 태그들이 다시 그려진다. - DOM Tree변한다.
//그래서 시작이 두번 출력된건가
const SchedulePage = () => {
    console.log('시작')
    const [events, setEvents] = useState([])
    const getEventList = () => {
        axios.get("/events.json").then(res => {
            console.log(res.data)
            setEvents(res.data)
            console.log(events)
        })
    }
    useEffect(() => {//백엔드 개발자 작성한 코드와 만나는 부분
        console.log('useEffect')
        getEventList()
    },[])
    return (
        <Fragment>
            <FullCalendar
                plugins={[ dayGridPlugin ]}
                initialView="dayGridMonth"
                weekends={true}
                events={[
                    { title: 'event 1', date: '2025-02-01' },
                    { title: 'event 2', date: '2025-02-28' }
                ]}
                />
        </Fragment>
    )
}

export default SchedulePage
//여러 개의 태그를 사용할 때는 반드시 최 외곽에 빈태그로 묶어 줄것.
//div와 Fragment 차이점은 div는 남지만 Fragment는 안 보임