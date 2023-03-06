import { StatusBar } from 'expo-status-bar';
import { StyleSheet, Text, View } from 'react-native';
import React, { Component, useContext } from 'react';
import AuthContext from './store/context/AuthContext';
import { NavigationContainer, useNavigation } from '@react-navigation/native';
import LogInScreen from './screens/LogInScreen';
import RegisterScreen from './screens/RegisterScreen';
import MainScreen from './screens/MainScreen';
import { AuthProvider } from './store/context/AuthContext';
import UserScreen from './screens/UserScreen';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { useEffect } from 'react';
import { saveUserWithEmail } from './store/redux/actions/UserActions';
import { Provider } from 'react-redux';
import { dispatchStore, RootState } from './store/redux/store';
import { store } from './store/redux/store';
import AsyncStorage from '@react-native-async-storage/async-storage';
import AddOrUpdateDepartmentScreen from './screens/AddOrUpdateDepartmentScreen';
import UpdateInfoScreen from './screens/UpdateInfoScreen';
import DepartmentScreen from './screens/DepartmentScreen';
import EmployeeScreen from './screens/EmployeeScreen';
import colors from './constants/colors';
import AddOrUpdateEmployeeScreen from './screens/AddOrUpdateEmployeeScreen';



const Stack = createNativeStackNavigator();

//Set time to refresh token
let timeForRefreshToken = 10 * 1000;

export type RootParamList = {
  MainScreen: undefined,
  RegisterScreen: undefined,
  LogInScreen: undefined,
  UserScreen: undefined,
  UpdateInfoScreen: undefined,
  DepartmentScreen: undefined,
  AddOrUpdateDepartmentScreen: {
    id?: number
  },
  EmployeeScreen: undefined,
  AddOrUpdateEmployeeScreen: {
    id?: number
  }
}

//Route for Main Screen, Register Screen, and Log In Screen


const RootApp = () => {

  //Get auth context
  let {isAuthenticated, logIn, logOut, refreshToken, email, updateToken, token} = useContext(AuthContext);


  //Set effect to get stored token in async storage
  useEffect(() => {
    async function getToken () {
      let storedToken = await AsyncStorage.getItem("token");

      if (storedToken) {
        
        logIn(JSON.parse(storedToken));
      }
    }

    getToken();
  },[]);
 
  //Set effect to set interval to refresh token

  useEffect(() => {

    if (isAuthenticated && refreshToken !== undefined || null) {
        let idInterval = setInterval(() => {
          updateToken(refreshToken as string, email as string);
        }, timeForRefreshToken);

        return () => clearInterval(idInterval);
    } 

  },[isAuthenticated, refreshToken, email])


  //Set effect to save user information

  useEffect(() => {
    
    if (isAuthenticated) {
      dispatchStore(saveUserWithEmail(email as string, token as string) as any);
    }

  },[isAuthenticated, email, token]);

  
  return (

    <Stack.Navigator>
    {/* Main Screen */}
    {!isAuthenticated && <Stack.Screen name='MainScreen' component={MainScreen} options={{
     headerShown: false
   }}/>}

    {/* Register Screen */}
   {!isAuthenticated &&<Stack.Screen name="RegisterScreen" component={RegisterScreen} options={{
     headerShown: false
   }}/>}

    {/* Log In Screen */}
   {!isAuthenticated &&<Stack.Screen name="LogInScreen" component={LogInScreen} options={{
     headerShown: false
   }}/>}

    {/* User Screen */}
    {isAuthenticated && (<Stack.Screen name="UserScreen" component={UserScreen} options={{
      headerStyle: {backgroundColor: colors.blue},
      headerTitle: "User Information"
    }}/>)}

    {/* Update Personal Info Screen */}
    <Stack.Screen name="UpdateInfoScreen" component={UpdateInfoScreen} options={{
      presentation: "modal",
      headerStyle: {backgroundColor: colors.blue},
      headerTitle: "Update Personal Information"
    }}/>

    {/* Deparment List Screen */}
    <Stack.Screen name="DepartmentScreen" component={DepartmentScreen} options={{
          headerStyle: {backgroundColor: colors.blue},
          title: "Department"
    }}/>

    {/* Add or Update Department Screen */}
    <Stack.Screen name="AddOrUpdateDepartmentScreen" component={AddOrUpdateDepartmentScreen} options={{
        presentation: "modal",
        headerStyle: {backgroundColor: colors.blue},
    }}/> 

    {/* Employee List Screen */}
    <Stack.Screen name="EmployeeScreen" component={EmployeeScreen} options={{
        headerTitle: "Employee",
        headerStyle: {backgroundColor: colors.blue},
        headerLeft: () => {
          return ""
        }
    }}/>

    {/* Add or Update Employee Screen */}

    <Stack.Screen name="AddOrUpdateEmployeeScreen" component={AddOrUpdateEmployeeScreen} options={{
        presentation: "modal",
        headerStyle: {backgroundColor: colors.blue}
    }}/>
   
 </Stack.Navigator>
  )
  
};

 
export default function App() {

  


  return (
    <> 
      <Provider store={store}>
      <AuthProvider>
        <NavigationContainer>
        <RootApp />
        </NavigationContainer>
      </AuthProvider>
      </Provider>
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
