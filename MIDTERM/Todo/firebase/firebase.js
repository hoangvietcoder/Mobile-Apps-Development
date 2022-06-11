
// firebase
import { initializeApp } from "firebase/app"
import {getAuth, createUserWithEmailAndPassword, signInWithEmailAndPassword} from 'firebase/auth'
import { getFirestore } from 'firebase/firestore/lite'

const firebaseConfig = {
  apiKey: "AIzaSyBGkDvbkjwbMIKyaqHvdNo0yr5Swjz62EI",
  authDomain: "todoapp-45a80.firebaseapp.com",
  projectId: "todoapp-45a80",
  storageBucket: "todoapp-45a80.appspot.com",
  messagingSenderId: "442956635216",
  appId: "1:442956635216:web:f9bbb014dc9171d979c555"
}

// Initialize Firebase
const app = initializeApp(firebaseConfig)
export const auth = getAuth(app)

export const RegisterFirebase = async (email, password) => {
  return await createUserWithEmailAndPassword(auth, email, password)
}

export const LoginFirebase = async (email, password) => {
  return await signInWithEmailAndPassword(auth, email, password)
}

export const db = getFirestore()

