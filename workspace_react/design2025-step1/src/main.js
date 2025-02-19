// Home 섹션을 아래로 스크롤시 투명하게 처리함.
const header = document.querySelector('.header');
const headerHeight = header.offsetHeight;
document.addEventListener('scroll', () => {
    if (window.scrollY > headerHeight) {
        header.classList.add('header-dark');
    } else {
        header.classList.remove('header-dark');
    }
});


const home = document.querySelector('.home_container');
const homeHeight = home.offsetHeight;
document.addEventListener('scroll', () => {
    console.log(1 - window.screenY / homeHeight);
    //y좌표가 즉 높이가 0인 경우에는 opacity를 1을 주고 
    //스크롤이 중간 즉 50인 경우에는 opacity를 0.5을 주고
    //스크롤이 100인 경우에는 opacity를 0을 준다.
    home.style.opacity = 1 - window.screenY / homeHeight;
})