import "../styles/todo-lists.scss"
import "bootstrap-icons/font/bootstrap-icons.css"
import {getInputValue} from "./inputValue";
import {showAlert} from "./Alert";
import {showList} from "./showList";
import {addTask} from "./addTask";
import {deleteTask} from "./deleteTask";
import {changeStatus} from "./changStatus";

const inputBtn = document.querySelector("#add-todo");
const ul =document.querySelectorAll("ul")

document.addEventListener("load", showList)
showList()

function addTaskHandler() {
    let inputValue = getInputValue()
    if (inputValue) {
        addTask(inputValue);
    } else {
        showAlert();
    }
}


function deleteTaskHandler(e) {
    if(e.target.matches(".bi-trash")) {
        deleteTask(e)
    }
}


function changeStatusHandler(e) {
    if(e.target.matches(".input-checkbox")) {
        changeStatus(e)

    }
}
document.addEventListener("change", changeStatusHandler)
inputBtn.addEventListener("click", addTaskHandler)
ul.forEach(list => list.addEventListener("click", deleteTaskHandler))
