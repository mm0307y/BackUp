import { appInterceptor  } from "../common/appInterceptor";

export const signUp = async(data) => {
    try {
        const response = await appInterceptor.post("api/v1/auth/signup", data)
        return response.data
    } catch (error) {
        console.error("회원가입 에러 발생 : "+error)
        //throw error;        
    }
}