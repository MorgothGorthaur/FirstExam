import {useState} from "react";
import {useEffect} from "react";
import ManufacturerService from "../API/ManufacturerService";
import Loader from "../UI/Loader/Loader";
import {Button, Modal} from "react-bootstrap";
import SouvenirForm from "./SouvenirForm";
import Souvenir from "./Souvenir";
import SouvenirService from "../API/SouvenirService";

const ManufacturersSouvenirsList = ({id}) => {
    const [souvenirs, setSouvenirs] = useState([]);
    const [loading, setLoading] = useState(false);
    const [modal, setModal] = useState(false);
    useEffect(() => {
        setLoading(true);
        setTimeout(() => {
            ManufacturerService.getManufacturersSouvenirs(id).then(data => setSouvenirs(data));
            setLoading(false);
        }, 1000);
    }, []);
    const add = (souvenir) => {
        setSouvenirs([...souvenirs, souvenir]);
        setModal(false);
    }
    const remove = (id) => {
        SouvenirService.remove(id);
        setSouvenirs(souvenirs.filter(s => s.id !==id));
    }
    return (
        <div>
            {
                loading ? (
                    <Loader/>
                ) : (
                    <div>
                        {
                            souvenirs.length ? (
                                <div>
                                    {
                                        souvenirs.map(souvenir => <div key={souvenir.id}>
                                                <Souvenir souvenir={souvenir} remove={remove}/>
                                            </div>
                                        )
                                    }
                                </div>
                            ) : (
                                <h1> souvenirs not founded! </h1>
                            )
                        }
                        <Button onClick={() => setModal(true)}> add </Button>
                        <Modal show={modal} onHide={setModal}> <SouvenirForm CreateOrUpdate={add} id={id}/> </Modal>
                    </div>
                )
            }
        </div>
    );
};
export default ManufacturersSouvenirsList;