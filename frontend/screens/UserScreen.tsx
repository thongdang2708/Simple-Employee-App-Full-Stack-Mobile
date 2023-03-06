import React, { useEffect } from 'react';
import {View, Text} from "react-native";
import { useLayoutEffect } from 'react';
import { useContext } from 'react';
import AuthContext from '../store/context/AuthContext';
import colors from '../constants/colors';
import { dispatchStore } from '../store/redux/store';
import { removeUser } from '../store/redux/actions/UserActions';
import {Ionicons} from "@expo/vector-icons";
import { StyleSheet } from 'react-native';
import { useSelector } from 'react-redux';
import { RootState } from '../store/redux/store';
import { getManagerUserInformation } from '../store/redux/actions/ManagerActions';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import ButtonForMainScreen from '../ui/ButtonForMainScreen';
import { RootParamList } from '../App';
import ButtonForUserScreen from '../ui/ButtonForUserScreen';

function UserScreen({navigation, route} : any) {

    //Get auth context 
    let {email, logOut, token} = useContext(AuthContext);

    //Get user state from redux
    let {id, managerId, username} = useSelector((state : RootState) => state.user);

    //Get auth context
    let {isAuthenticated} = useContext(AuthContext);

    //Get manager state from redux
    let {manager, isSuccess, isError, message} = useSelector((state : RootState) => state.manager);

    //Set individual navigation
    let navigation2 = useNavigation<StackNavigationProp<RootParamList>>();
    

    //Handle log out 
    const handleLogout = () => {
        logOut();
        dispatchStore(removeUser() as any);
    };

    
    //Set layout effect
    useLayoutEffect(() => {
       navigation.setOptions({
        headerRight: () => {
            return <Ionicons name="log-out-outline" size={36} color={colors.neon} onPress={handleLogout}/>
        },
       });
    },[navigation]);

    //Set effect to fetch information of manager

    useEffect(() => {
        dispatchStore(getManagerUserInformation(managerId, token as string) as any);
    },[managerId]);


    //Handle press to update page
    const handleMovingPageToUpdatePage = () => {
        navigation2.navigate("UpdateInfoScreen");
    }

    useEffect(() => {

        if (!isAuthenticated || token == null) {
            navigation2.navigate("LogInScreen");
        }
    },[isAuthenticated, token])

    //Handle press to move to employee or department page

    const handleMovingPageToEmployeeOrDepartment = (name : string) => {
        if (name === "department") {
            navigation2.navigate("DepartmentScreen");
        } else {
            navigation2.navigate("EmployeeScreen");
        }
    };

    //Render when there is an error
    if (isError) {
        return (
            <View style={styles.userScreen}>
            <Text style={styles.errorText}> User Screen </Text> 
            
        </View>
        )
    }


  return (  
   <View style={styles.userScreen}>
       <Text style={styles.title}> Personal User Information </Text> 

        <View style={styles.details}>
        <Text style={styles.information}> Username : <Text style={styles.personalInfo}> {username}  </Text> </Text> 
        <Text style={styles.information}> City: <Text style={styles.personalInfo}> {manager.city === "" ? "No Information" : manager.city} </Text> </Text>
        <Text style={styles.information}> Address: <Text style={styles.personalInfo}> {manager.address === "" ? "No Information" : manager.address} </Text> </Text> 
       

        <View style={styles.buttonContainer}>
        <ButtonForUserScreen onPress={handleMovingPageToUpdatePage}>
            Update Personal Information
        </ButtonForUserScreen>
        </View>
        
        </View>

        <View style={[styles.buttonContainer, styles.outerButtonContainer]}>
        <ButtonForUserScreen onPress={() => handleMovingPageToEmployeeOrDepartment("department")}>
            To Department List
        </ButtonForUserScreen>

        <ButtonForUserScreen onPress={() => handleMovingPageToEmployeeOrDepartment("employee")}>
            To Employee List
        </ButtonForUserScreen>
        </View>
      
   </View>
  )
}

const styles = StyleSheet.create({
    userScreen: {
        flex: 1,
        padding: 10,
        backgroundColor: colors.skinColor
    },
    title: {
        textAlign: "center",
        fontWeight: "bold",
        marginVertical: 10,
        fontSize: 20
    },
    errorText: {
        color: colors.red,
        fontSize: 20,
        textAlign: "center",
        marginVertical: 15
    },
    details: {
        marginVertical: 10,
        padding: 15,
        borderWidth: 2,
        backgroundColor: colors.offWhite,
        borderRadius: 4
    },
    information: {
        fontWeight: "bold",
        marginVertical: 10,
        fontSize: 18
    },
    personalInfo: {
        textDecorationLine: "underline"
    },
    buttonContainer: {
        alignItems: "center",
        justifyContent: "center"
    },
    outerButtonContainer: {
        flexDirection: "row",
        justifyContent: "space-around"
    }
});

export default UserScreen