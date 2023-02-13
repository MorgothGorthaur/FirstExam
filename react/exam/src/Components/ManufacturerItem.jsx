import {Button, Modal} from "react-bootstrap";
import ManufacturerForm from "./ManufacturerForm";
import React, {useState} from "react";

const ManufacturerItem = ({manufacturer, removeManufacturer, updateManufacturer}) => {
    const [modal, setModal] = useState(false);
    const update =(data) => {
        setModal(false);
        updateManufacturer(data);
    }
    return (
        <div>
            <h1>{manufacturer.name}</h1>
            <h1>{manufacturer.country}</h1>
            <Button variant="danger"
                    onClick={() => removeManufacturer(manufacturer.id)}> remove </Button>
            <Button onClick={() => setModal(true)}> update </Button>
            <Modal show={modal} onHide={setModal}><ManufacturerForm
                CreateOrUpdate={update}
                manufacturer={manufacturer}/>
            </Modal>
        </div>
    )
};
export default ManufacturerItem;