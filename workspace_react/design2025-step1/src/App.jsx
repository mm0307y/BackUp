import { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";

const App = () => {
  const [isScrolled, setIsScrolled] = useState(false);

  // 필요한 DOM 요소들을 참조하기 위한 ref 생성
  const navbarRef = useRef(null);
  const navbarMenuRef = useRef(null);
  const burgerToggleRef = useRef(null);
  const homeContentRef = useRef(null);
  const upBtnRef = useRef(null);

  // 특정 섹션으로 부드럽게 스크롤하는 함수
  const scrollIntoView = (selector) => {
    const targetElem = document.querySelector(selector);
    if (targetElem) {
      targetElem.scrollIntoView({ behavior: "smooth" });
    }
  };

  useEffect(() => {
    const navbar = navbarRef.current;
    const navbarMenu = navbarMenuRef.current;
    const burgerToggle = burgerToggleRef.current;
    const homeContent = homeContentRef.current;
    const upBtn = upBtnRef.current;

    // 스크롤 이벤트 핸들러 (네비게이션 바 배경, 홈 컨텐츠 투명도, 업 버튼 노출 제어)
    const handleScroll = () => {
      if (navbar) {
        const navbarHeight = navbar.offsetHeight;
        if (window.scrollY > navbarHeight) {
          navbar.classList.add("header--dark");
          setIsScrolled(true);
        } else {
          navbar.classList.remove("header--dark");
          setIsScrolled(false);
        }
      }

      // 홈 컨텐츠의 투명도 조절 (스크롤에 따라 서서히 사라지도록)
      if (homeContent) {
        const homeHeight = homeContent.offsetHeight;
        let newOpacity = 1 - window.scrollY / homeHeight;
        newOpacity = newOpacity < 0 ? 0 : newOpacity;
        homeContent.style.opacity = newOpacity;
      }

      // 스크롤 위치에 따라 업 버튼의 보임/숨김 처리
      if (upBtn && homeContent) {
        const homeHeight = homeContent.offsetHeight;
        if (window.scrollY > homeHeight / 2) {
          upBtn.classList.add("visible");
        } else {
          upBtn.classList.remove("visible");
        }
      }
    };

    // 스크롤 이벤트 등록
    window.addEventListener("scroll", handleScroll);

    // 네비게이션 메뉴 클릭 시 해당 섹션으로 스크롤 처리
    const handleNavbarMenuClick = (event) => {
      const link = event.target.dataset.link;
      if (!link) return;
      scrollIntoView(link);
      // 모바일 메뉴(토글된 메뉴)가 열려 있다면 닫기
      if (navbarMenu.classList.contains("open")) {
        navbarMenu.classList.remove("open");
      }
    };

    if (navbarMenu) {
      navbarMenu.addEventListener("click", handleNavbarMenuClick);
    }

    // 버거 아이콘 클릭 시 메뉴 열고 닫기 토글
    const handleBurgerToggle = () => {
      if (navbarMenu) {
        navbarMenu.classList.toggle("open");
      }
    };

    if (burgerToggle) {
      burgerToggle.addEventListener("click", handleBurgerToggle);
    }

    // 업 버튼 클릭 시 홈 섹션으로 스크롤
    const handleUpBtnClick = () => {
      scrollIntoView("#home");
    };

    if (upBtn) {
      upBtn.addEventListener("click", handleUpBtnClick);
    }

    // 컴포넌트 언마운트 시 이벤트 리스너 정리
    return () => {
      window.removeEventListener("scroll", handleScroll);
      if (navbarMenu) {
        navbarMenu.removeEventListener("click", handleNavbarMenuClick);
      }
      if (burgerToggle) {
        burgerToggle.removeEventListener("click", handleBurgerToggle);
      }
      if (upBtn) {
        upBtn.removeEventListener("click", handleUpBtnClick);
      }
    };
  }, []);

  return (
    <>
      {/* header */}
      <header class="header">
        <div class="header_logo">
          <img class="header_logo_img" src="images/man2.png" alt="logo" />
          <h1 class="header_logo_title"><Link>Portfolio</Link></h1>
        </div>
        <nav id="navbar" ref={navbarRef} className={isScrolled ? "header--dark" : ""}>
          <ul className="navbar-menu" ref={navbarMenuRef}>
            <li className="navbar-menu-item active" data-link="#home">Home</li>
            <li className="navbar-menu-item" data-link="#me">나에 대해...</li>
            <li className="navbar-menu-item" data-link="#skill">보유 기술...</li>
            <li className="navbar-menu-item" data-link="#project">참여프로젝트</li>
            <li className="navbar-menu-item" data-link="#contact">Contact</li>
          </ul>
        </nav>
          <button className="burger-toggle-btn" ref={burgerToggleRef}>
            <i className="fas fa-bars"></i>
          </button>
      </header>      

      <main>

        {/* home */}
        <section id="home">
          <div id="home_container">
            <div className="hcontent" ref={homeContentRef}>
              {/* 홈 섹션에 들어갈 내용 */}
              <img src="../images/man6.gif"  alt="KH's profile photo" className="home_avatar" />
              <h2 class="home_title">
                풀스택 개발자<strong class="home_title_strong"> , 나신입</strong> 입니다.
              </h2>
            <p class="home_description">
              문제 해결과 협업에 강한 풀스택 개발자 – 안정적인 시스템 구축과 창의적 아이디어로 새로운 가능성을 열어갑니다.
            </p>
            <Link className="home_contact">Contact Me</Link>
            </div>
          </div>
        </section>

        {/* me */}
        <section id="me" className="section">
          <div className="mcontent">나에 대해...</div>
        </section>

        {/* skill */}
        <section id="skill" className="section">
          <div className="scontent">보유기술...</div>
        </section>

        {/* project */}
        <section id="project" className="section">
          <div className="pcontent">참여프로젝트</div>
        </section>

        {/* contact */}
        <section id="contact" className="section">
          <div className="ccontent">
            <h2 className="contact-email">이메일 주소</h2>
            <div className="contact-links">
              <Link to="https://github.com">
                <i className="fab fa-github"></i>
              </Link>
              <Link to="https://www.linkedin.com">
                <i className="fa fa-linkedin-square"></i>
              </Link>
            </div>
            <p className="contact-rights">2025 김유신 - All rights reserved</p>
          </div>
        </section>
        {/* Up 버튼 */}
        <button className="up" ref={upBtnRef}>
          <i className="fas fa-arrow-up"></i>
        </button>
      </main>

    </>
  );
};

export default App;
