import axios from "axios";
import AuthContext from "../context/AuthContext";
import { useContext } from "react";
import { WEB_URL } from "@env";



//Set context for auth

let {saveSuccessAlert, saveFailAlert, logIn, logOut} = useContext(AuthContext);
//Props for user inputs

export interface UserInputProps {
    username?: string,
    email?: string,
    password?: string,
}

interface RefreshToken {
    refreshToken: string
}

//Register user

export const registerUser = async (userInputs: UserInputProps) => {

    try {

    let response = await axios.post(WEB_URL + "/auth/register", userInputs);

    let data = response.data;

    saveSuccessAlert(data.message);

    } catch (error) {
    
        let castError = error as any;

        let message = (castError.response && castError.response.data && castError.response.data.message) || castError.message || castError.toString();

        saveFailAlert(message);
    }
};

//Log In user

export const logInUser = async (userInputs : UserInputProps) => {
    try {

        let response = await axios.post(WEB_URL + "/login", userInputs);
    
        let data = response.data;
    
        saveSuccessAlert();
        logIn(data.access_token);
    
        } catch (error) {
        
            let castError = error as any;
    
            let message = (castError.response && castError.response.data && castError.response.data.message) || castError.message || castError.toString();
    
            saveFailAlert(message);
        }
}


//Log out user

export const logOutFunction = async () => {

    await axios.get(WEB_URL + "/logout");
}

//Update refresh token

export const updateRefreshToken = async (refreshToken : RefreshToken) => {

    let response = await axios.post(WEB_URL + "/auth/refreshToken", refreshToken);

    let data = response.data;

    return {
        accessToken: data.accessToken,
        refreshToken: data.refreshToken
    }

};
 