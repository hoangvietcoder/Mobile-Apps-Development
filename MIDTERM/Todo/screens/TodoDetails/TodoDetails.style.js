import { StyleSheet } from "react-native";
import { Colors } from "../../constants/Colors";

export const styles =StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.white
  },
  icon: {
    padding: 5,
    fontSize: 12,
    color: Colors.white
  },
  addIcon: {
    position: 'absolute',
    bottom: 40,
    right: 20,
    zIndex: 100,
    backgroundColor: Colors.primary,
    width: 60,
    height: 60,
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 50,
    shadowColor: "#000",
    shadowOffset: {
        width: 0,
        height: 5,
    },
    shadowOpacity: 0.34,
    shadowRadius: 6.27,
    
    elevation: 10,
  }
})