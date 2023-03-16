import {createNewTask} from "./createNewTask";
import {fetch_post_task} from "./API";
import {clearInput} from "./inputValue";
import {alert} from "./Alert";
import {add_task_to_taskList, taskList} from "./taskList";
import {renderList} from "./renderHTMLList";

export function addTask(input) {
    const task = createNewTask(input);
    console.log(task)
    fetch_post_task(task)
        .then((newTask) => {
            console.log(newTask)
            add_task_to_taskList(newTask);
            renderList(taskList)
            console.log(document.querySelector("#todo-lists li"));
            clearInput(input);
            alert();
        })
        .catch(err => console.log(err))
}

