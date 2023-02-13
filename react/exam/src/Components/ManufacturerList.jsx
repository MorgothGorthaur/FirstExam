import React, {useState, useEffect} from "react";
import Loader from '../UI/Loader/Loader';
import ManufacturerService from "../API/ManufacturerService";
import {Button, Modal} from "react-bootstrap";
import ManufacturerForm from "./ManufacturerForm";
import ManufacturerItem from "./ManufacturerItem";

const ManufacturerList = () => {
    const [manufacturers, setManufacturers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(false);
    useEffect(() => {
        setLoading(true);
        setTimeout(() => {
            fetchManufacturers();
            setLoading(false);
        }, 1000);
    }, []);

    async function fetchManufacturers() {
        setManufacturers(await ManufacturerService.getAll());
    }

    const addManufacturer = (manufacturer) => {
        setManufacturers([...manufacturers, manufacturer]);
        setModal(false);
    }

    const removeManufacturer = (id) => {
        ManufacturerService.removeManufacturer(id);
        setManufacturers(manufacturers.filter(m => m.id !== id));
    }
    return (
        <div>
            {loading ? (
                <div>
                    <Loader/>
                </div>
            ) : (
                <div>
                    {manufacturers.length ? (
                        <div>
                            {manufacturers.map(manufacturer =>
                                <div>
                                    <ManufacturerItem manufacturer={manufacturer}/>
                                    <Button variant="danger"
                                            onClick={() => removeManufacturer(manufacturer.id)}> remove </Button>
                                </div>
                            )
                            }
                        </div>
                    ) : (
                        <div>
                            <h1> manufacturers not founded! </h1>
                        </div>
                    )}
                    <Button onClick={() => setModal(true)}> add manufacturer</Button>
                    <Modal show={modal} onHide={setModal}> <ManufacturerForm CreateOrUpdate={addManufacturer}/> </Modal>
                </div>
            )}
        </div>
    );
};
export default ManufacturerList;
