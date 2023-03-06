
import { useReducer } from "react";
import { useState } from "react";
import React, { createContext, useContext } from "react";
import { logOutFunction, updateRefreshToken } from "../http/AuthHttp";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import { WEB_URL } from "@env";



//Set interface for children props
interface ChildrenProps {
    children: React.ReactNode
}

//Set interface for auth props
interface AuthContext {
    token: string | null | undefined,
    email: string | null | undefined,
    refreshToken: string | null | undefined,
    isAuthenticated: boolean,
    isSuccess: boolean,
    isError: boolean,
    message: "",
    saveSuccessAlert: (message? : string) => void,
    saveFailAlert: (message : string) => void,
    updateToken: (refreshToken : string, email : string) => void,
    removeError: () => void,
    logIn: (userInfo : TokenProps) => void,
    logOut: () => void
}

//Set initial values for Auth Context
let initialAuthValues : AuthContext = {
    token: "",
    email: "",
    refreshToken: "",
    isAuthenticated: false,
    isSuccess: false,
    isError: false,
    message: "",
    saveSuccessAlert: (message? : string) => {},
    saveFailAlert: (message : string) => {},
    updateToken: (refreshToken : string, email : string) => {},
    removeError: () => {},
    logIn: (userInfo : TokenProps) => {},
    logOut: () => {}
};

let AuthContext = createContext<AuthContext>(initialAuthValues);

//Set interface for action

interface Action {
    type: string,
    payload?: any
}

const authReducer = (state : AuthContext = initialAuthValues, action: Action) => {

    switch (action.type) {
        case "LOG_IN_SUCCESS":
            return {
                ...state,
                isSuccess: true,
                message: action.payload
            }
        case "LOG_IN_FAIL":
            return {
                ...state,
                isError: true,
                message: action.payload
            }
        case "RESET_STATE_SUCCESS":
            return {
                ...state,
                isSuccess: false,
                message: ""
            } 
        case "RESET_STATE_FAIL":
            return {
                ...state,
                isError: false,
                message: ""
            }
        case "REMOVE_ERROR":
            return {
                ...state,
                isError: false,
                message: ""
            }

        default: 
            return state
    }
}

interface TokenProps {
    token: string,
    email: string,
    refreshToken: string
}

export const AuthProvider = ({children}: ChildrenProps) => {
    //Set reducer for auth
    let [state, dispatch] = useReducer(authReducer, initialAuthValues);

    //Set auth token
    let [authToken, setAuthToken] = useState<string|null|undefined>(undefined);

    //Set email state
    let [email, setEmail] = useState<string | undefined | null>(undefined);

    //Set refreshToken
    let [refreshToken, setRefreshToken] = useState<string | undefined | null>(undefined);


    //Save success alert
    let saveSuccessAlert = (message? : string) => {

        dispatch({
            type: "LOG_IN_SUCCESS",
            payload: message
        })

        setTimeout(() => {
            dispatch({
                type: "RESET_STATE_SUCCESS"
            })
        }, 8000);
    }

    //Save fail alert
    let saveFailAlert = (message : string) => {
        dispatch({
            type: "LOG_IN_FAIL",
            payload: message
        })

        setTimeout(() => {
            dispatch({
                type: "RESET_STATE_FAIL"
            })
        }, 8000);
    }
 
    //Register user
    let logIn = (userInfo: TokenProps) => {
        setAuthToken(userInfo.token);
        setEmail(userInfo.email);
        setRefreshToken(userInfo.refreshToken);
        AsyncStorage.setItem("token", JSON.stringify(userInfo));
   
    }

    //Update token based on refresh token

    let updateToken = async (refreshToken : string, email : string) => {

        let response = await axios.post(WEB_URL + "/auth/refreshToken", {
            refreshToken: refreshToken
        });

        let data = response.data;

        let newData = {
            token: data.accessToken,
            email: email,
            refreshToken: data.refreshToken
        };

        setAuthToken(newData.token);
        setEmail(newData.email);
        setRefreshToken(newData.refreshToken);
        AsyncStorage.setItem("token", JSON.stringify(newData));
        
    }

    //Log out
    let logOut = async () => {
        await axios.delete(WEB_URL + "/auth/logout");
        setAuthToken(null);
        setEmail(null);
        setRefreshToken(null);
        AsyncStorage.removeItem("token");
        AsyncStorage.removeItem("user");
        
    }

    //Remove error when changing auth pages
    let removeError = () => {
        dispatch({
            type: "REMOVE_ERROR"
        })
    };
    

    return (<AuthContext.Provider value={{
        token: authToken,
        email: email,
        isAuthenticated: !!authToken,
        refreshToken: refreshToken,
        isSuccess: state.isSuccess,
        isError: state.isError,
        message: state.message,
        saveSuccessAlert: saveSuccessAlert,
        saveFailAlert: saveFailAlert,
        updateToken: updateToken,
        removeError: removeError,
        logIn: logIn,
        logOut: logOut
    }}>
        {children}
    </AuthContext.Provider>)
}

export default AuthContext;