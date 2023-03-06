import React from 'react';
import {View, Text, StyleSheet} from "react-native";

import { SafeAreaView } from 'react-native-safe-area-context';
import colors from '../constants/colors';
import ButtonForMainScreen from '../ui/ButtonForMainScreen';
import { RootParamList } from '../App';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';

function MainScreen() {

    //Set navigation 
    let navigation = useNavigation<StackNavigationProp<RootParamList>>();

    //Handle Move Page
    let handleMovePage = (page : string) => {

        if (page === "RegisterScreen") {
            navigation.navigate("RegisterScreen");
        } else {
            navigation.navigate("LogInScreen");
        }
    }
    
  return (
    <SafeAreaView style={styles.screen}>
    <View style={styles.innerBody}> 
        <Text style={styles.title}> Welcome To Employee Management </Text>
        <Text style={styles.title}> Application </Text>

    <View style={styles.buttons}>
        <ButtonForMainScreen onPress={() => handleMovePage("RegisterScreen")}>
           <Text> To Register Screen </Text>
        </ButtonForMainScreen>

        <ButtonForMainScreen onPress={() => handleMovePage("LogInScreen")}>
        <Text> To Log In Page </Text>
        </ButtonForMainScreen>
    </View>
    </View>

   
    </SafeAreaView>
  )
}

const styles = StyleSheet.create({
    screen: {
        flex: 1,
        backgroundColor: colors.blue
    },
    innerBody: {
        marginHorizontal: 15,
        textAlign: "center",
        flex: 1,
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
    },
    title: {
        fontSize: 20,
        marginVertical: 12,
        color: "black",
        fontWeight: "bold"
    },
    buttons: {
        marginVertical: 20,
        flexDirection: "row"
    }
}); 

export default MainScreen