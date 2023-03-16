import {fetch_get_tasks} from "./API";
import {renderList} from "./renderHTMLList";
import {initialize_taskList, taskList} from "./taskList";

export function showList() {
    console.log("refresh");
     fetch_get_tasks()
    .then(data => {
        initialize_taskList(data);
        console.log(data)
        renderList(taskList)
    })
}
