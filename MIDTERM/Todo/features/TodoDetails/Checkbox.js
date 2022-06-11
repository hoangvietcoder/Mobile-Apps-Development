import { Text, View, TouchableOpacity  } from "react-native"
import { StyleSheet } from "react-native"
import { Colors } from "../../constants/Colors"

export const Checkbox = ({isChecked, onChecked, ...props}) => {
  return ( 
      <TouchableOpacity style={styles.checkbox} onPress={onChecked}>
          <Text style={{color: Colors.green, fontSize: 20, fontWeight: 'bold'}}>{isChecked ? "✓" : ""}</Text>
      </TouchableOpacity> 
  ) 
}

const styles = StyleSheet.create({
  checkbox: {
    width: 25,
    height: 25,
    margin: 5,
    backgroundColor: "#fff0",
    color: Colors.lightGray,
    borderWidth: 1,
    borderRadius: 3,
    borderColor: Colors.lightGray,
    alignItems: "center",
    justifyContent: "center",
    marginRight: 10
  },
})