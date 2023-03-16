import {fetch_delete_task} from "./API";

function get_deleted_id(e) {
    return e.target.dataset.id
}

export function deleteTask(e) {
    e.target.parentNode.remove()
    const ID = get_deleted_id(e);
    fetch_delete_task(ID);
}
