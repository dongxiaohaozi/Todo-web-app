import {fetch_change_status} from "./API";
import {renderList} from "./renderHTMLList";
import {taskList} from "./taskList";

function changStatus(e) {
    const changedTaskName = e.target.nextElementSibling.innerHTML.trim();
    const originStatus = e.target.checked
    const changedID = Number(e.target.nextElementSibling.dataset.id)
    return {name: changedTaskName, id: changedID, completed: originStatus}
}

function amend_status_in_tasklist(task) {
    taskList.find(element => element.id === task.id).completed = task.completed
}

export function changeStatus(e) {
    const change_task = changStatus(e);
    fetch_change_status(change_task).then(task => {
            amend_status_in_tasklist(task);
            renderList(taskList)
        }
    )
}
