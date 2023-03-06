import React from 'react';
import { Pressable } from 'react-native';
import {View, Text, StyleSheet} from "react-native";
import colors from '../constants/colors';


interface ButtonProps {
    children: React.ReactNode,
    onPress: () => void
}


function ButtonForMainScreen({children, onPress} : ButtonProps) {
  return (
    <View style={styles.outerButton}>
    <Pressable onPress={onPress} style={({pressed}) => pressed && styles.pressedButton} android_ripple={{color: colors.offWhite}}>
    <View style={styles.innerButton}>
        <Text style={styles.textContent}> {children} </Text>
    </View>
    </Pressable>
    </View>
  )
}

const styles = StyleSheet.create({
    outerButton: {
        marginHorizontal: 10,
        marginVertical: 5,
        elevation: 4,
        shadowColor: "grey",
        shadowOffset: {width: 0, height: 2},
        shadowOpacity: 0.35,
        shadowRadius: 5
    },
    innerButton: {
        backgroundColor: colors.yellow,
        padding: 10,
        borderRadius: 10,
    },
    textContent: {
        fontWeight: "bold",
        textAlign: "center"
    },
    pressedButton: {
        opacity: 0.35
    }
});

export default ButtonForMainScreen