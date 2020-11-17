"use strict"

document.addEventListener("DOMContentLoaded", () => {
    const USUARIO = new Usuario();
    const CRUDGENERICO = new CRUDGenerico('');
    let importType = '';
    let datosTempPlan = {};

    loadHTML();

    function renderHTML(resp) {
        document.querySelector('#bodyContainer').innerHTML = resp;
    }

    function renderHome(data) {
        renderHTML(data);

        document.querySelector('.perfil-container__btnLogout').addEventListener('click', r => {
            sessionStorage.setItem('token', null);
            USUARIO.token = null;
            loadHTML();
        });

        document.querySelector('.viajes-container__btn').addEventListener('click', r => {
            mostrarViajes();
        });

        document.querySelector('.viajes-container__btnId').addEventListener('click', r => {
            $('#modalViajeId').modal('show');
        });

        document.querySelector('.viaje-container__btnViajeId').addEventListener('click', e => {
            const input = document.querySelector('.viaje-container__numeroId');
            if (input.value != '') {
                CRUDGENERICO.getModularAuthorization('viajes/' + input.value, USUARIO.token).then(resp => {
                    $('#modalViajeId').modal('hide');
                    console.log(resp);
                    if (resp) {
                        renderList(resp);
                    }
                }, err => {
                    console.log(err);
                })
            }
        });

        document.querySelector('.viajes-container__btnReport').addEventListener('click', r => {
            CRUDGENERICO.getModularAuthorization('viajes/reporte/', USUARIO.token).then(resp => {
                console.log(resp);

                renderList(resp);
            }, err => {
                if (err.status === 403) {
                    document.querySelector('#userStatusContainer').innerHTML = `<p> Usuario sin permisos. </p>`
                    $('#modalUserStatus').modal('show');
                }
                console.log(err);
            })
        });

        document.querySelector('.viajes-container__btnCargar').addEventListener('click', r => {
            importType = 'viaje';
            document.querySelector('#importType').innerHTML = 'Importar ' + importType;
            $('#modalViajeCargar').modal('show');
        });

        document.querySelector('.viajes-container__btnCargarPlan').addEventListener('click', r => {
            importType = 'plan';
            document.querySelector('#importType').innerHTML = 'Importar ' + importType;
            $('#modalViajeCargar').modal('show');
        });

        document.querySelector('.viaje-container__btnViajeCargar').addEventListener('click', r => {
            const input = document.querySelector('#importViaje');
            console.log(input.files);
            if (input.files.length > 0) {
                console.log('paso');

                const data = new FormData();
                data.append('file', input.files[0]);
                let url = '';

                if (importType === 'viaje') {
                    url = 'viajes/upload-file/';
                } else if (importType === 'plan') {
                    url = 'planes/upload-plan/';
                }

                CRUDGENERICO.postFormModularAuthorization(url, data, USUARIO.token).then(resp => {
                    console.log('Respuesta import bien', resp);
                    input.value = '';
                    $('#modalViajeCargar').modal('hide');
                    mostrarViajes();
                }, err => {
                    console.log(err);
                    alert('Error al importar.');
                })
            } else {
                alert('Debe seleccionar un archivo');
            }
        });

        document.querySelector('.viajes-container__btnCrear').addEventListener('click', r => {
            $('#modalViajeCrear').modal('show');
        });

        document.querySelector('.viaje-container__btnViajeCrear').addEventListener('click', r => {
            const name = document.querySelector('#travelName').value;
            const description = document.querySelector('#travelDescription').value;
            const destination = document.querySelector('#travelDestination').value;
            const initialDate = document.querySelector('#travelInitialDate').value;
            const endDate = document.querySelector('#travelEndDate').value;

            if ((name == '') || (description == '') || (destination == '') || (initialDate == '') || (endDate == '')) {
                alert('Verifique los datos ingresados');
            } else {
                const data = {
                    'nombre': name,
                    'descripcionBreve': description,
                    'destino': destination,
                    'fechaInicio': initialDate,
                    'fechaFin': endDate,
                }

                CRUDGENERICO.postModularAuthorization('viajes/', data, USUARIO.token).then(resp => {
                    $('#modalViajeCrear').modal('hide');
                    document.querySelector('.viajes-container__btn').click();
                }, err => {
                    alert('Error al crear viaje.');
                })

            }
        });

        document.querySelector('.viajes-container__btnCrearPlan').addEventListener('click', r => {
            CRUDGENERICO.getModularAuthorization('viajes/', USUARIO.token).then(resp => {
                console.log(resp);
                let select = document.querySelector('#selectViajes');
                let template = '<option value="-1" selected>Seleccione Viaje</option>';
                resp.forEach(v => {
                    template += `<option value="${v.id}" selected>${v.nombre}</option>`;
                });
                select.innerHTML = template;
                select.value = -1;
                $('#modalViajeCrearPlan').modal('show');
                //renderList(resp);
            }, err => {
                console.log(err);
            })
        });

        document.querySelector('.viaje-container__btnViajeCrearPlan').addEventListener('click', r => {
            const name = document.querySelector('#travelDescription2').value;
            const idViaje = document.querySelector('#selectViajes').value;
            const codReserva = document.querySelector('#travelName2').value;
            const initialDate = document.querySelector('#travelInitialDate2').value;
            const endDate = document.querySelector('#travelEndDate2').value;
            const aeropuertoOrigen = document.querySelector('#travelDescription3').value;
            const aeropuertoLlegada = document.querySelector('#travelDescription4').value;
            const compania = document.querySelector('#travelDescription5').value;
            const numVuelo = document.querySelector('#travelDescription6').value;
            const horasEscala = document.querySelector('#travelDescription7').value;
            const tipoAvion = document.querySelector('#travelDescription8').value;

            if ((selectViajes == -1) || (name == '') || (codReserva == '') || (initialDate == '') || (endDate == '')) {
                alert('Verifique los datos ingresados');
            } else {
                let data = {
                    'nombre': name,
                    'fechaInicio': initialDate,
                    'fechaFin': endDate,
                    'codigoReserva': codReserva,
                    'idViaje': idViaje
                }

                let ruta = 'planes/';

                if (aeropuertoOrigen != '' || aeropuertoLlegada != '' || compania != '' || numVuelo != '' || horasEscala != '' || tipoAvion != '') {
                    if (aeropuertoOrigen != '' && aeropuertoLlegada != '' && compania != '' && numVuelo != '' && horasEscala != '' && tipoAvion != '') {
                        ruta = 'planes/vuelos/';
                        data['aeropuertoSalida'] = aeropuertoOrigen;
                        data['aeropuertoLlegada'] = aeropuertoLlegada;
                        data['tipoAvion'] = tipoAvion;
                        data['tiempoEscalaMin'] = horasEscala;
                        data['compania'] = compania;
                        data['numVuelo'] = numVuelo;
                    } else {
                        alert('Faltan datos del vuelo');
                        return;
                    }
                }

                console.log(data);
                CRUDGENERICO.postModularAuthorization(ruta, data, USUARIO.token).then(resp => {
                    console.log(resp);
                    $('#modalViajeCrearPlan').modal('hide');
                    mostrarViajes();
                }, err => {
                    alert('Error al crear viaje.');
                })

            }
        });


    }

    function renderList(data) {
        const elem = document.querySelector('.main');
        let keys = '';
        const isArr = Array.isArray(data);
        if (isArr) {
            keys = Object.keys(data[0]);
        } else {
            keys = Object.keys(data);
        }
        let template = `<table class="table bg-light">
  		<thead class="thead-dark"><tr>`;
        for (let i = 0; i < keys.length; i++) {
            template += `<th scope="col">${keys[i]}</th>`;
        }
        template += `</tr>
  		</thead>
  		<tbody >`;


        if (isArr) {
            for (let i = 0; i < data.length; i++) {
                template += `<tr>`;
                for (let e = 0; e < keys.length; e++) {
                    const key = keys[e];
                    if (key === "planes") {
                        //						template += `<td>`
                        //						for(let u = 0; u < data[i][key].length; u++) {
                        //							template += `${data[i][key][u].nombre} - `;																	
                        //						}
                        //						template += `</td>`
                        //					    let objPlan = {};
                        //						objPlan["id"] = data[i].id;
                        //						objPlan["planes"] = data[i].planes; 
                        datosTempPlan[data[i].id] = data[i].planes;
                        console.log(datosTempPlan);
                        template += `<td><button class="btn btn-success verMasPlanes" data-id="${data[i].id}">Ver más</button></td>`;
                    } else {
                        template += ` <td>${data[i][key]}</td>`;
                    }
                }
                template += `</tr>`;
            }
        } else {
            template += `<tr>`;
            for (let e = 0; e < keys.length; e++) {
                const key = keys[e];
                template += ` <td>${data[key]}</td>`;
            }
            template += `</tr>`;
        }

        template += `</tbody>
		</table>`;

        elem.innerHTML = template;

        document.querySelectorAll(".verMasPlanes").forEach(btn => {
            btn.addEventListener("click", () => {
                const planesContainer = document.querySelector('.planes');
                planesContainer.innerHTML = '';

                const planes = datosTempPlan[btn.getAttribute("data-id")];

                if (planes.length > 0) {
                    planesContainer.innerHTML += '<div class="col">';
                    planesContainer.innerHTML += '<h3>Planes</h3>';

                    planesContainer.innerHTML += '<ul class="list-group">';
                    planes.forEach(plan => {
                        let datos = `${plan.id}, ${plan.nombre}, ${plan.fechaInicio}, ${plan.fechaFin}, ${plan.codigoReserva}`;
                        if (plan.hasOwnProperty('numVuelo')) {
                            datos += `${plan.numVuelo}, ${plan.compania}, ${plan.tiempoEscalaMin}, ${plan.tipoAvion}, ${plan.aeropuertoSalida}, ${plan.aeropuertoLlegada}`;
                        }
                        planesContainer.innerHTML += `<li class="list-group-item">${datos}</li>`;
                    })
                    planesContainer.innerHTML += '</ul>';

                    planesContainer.innerHTML += '</div>';
                }
            });
        });
    }

    function renderLogin(data) {
        renderHTML(data);
        // --------------------------USUARIO--------------------------
        document.querySelector('.btnLogin').addEventListener('click', (e) => {
            e.preventDefault();
            let nombre = document.querySelector("[name='usuario']").value;
            let clave = document.querySelector("[name='clave']").value;
            let data = {
                "nombre": nombre,
                "clave": clave
            }
            USUARIO.postModular("authentication/", data).then(resp => {
                if (resp.status === "Success") {
                    USUARIO.token = resp.token;
                    sessionStorage.setItem("token", USUARIO.token);
                    loadHTML();
                }

            }, err => {
                alert('Usuario o contraseña incorrectos');
                console.log(err.error);
            });
        });
    }


    function loadHTML() {
        USUARIO.getHome().then(r => {
            renderHome(r);
        }, err => {
            const data = `<div class="container" style="height: 100vh; width: 100vw;">
	        <div class="row h-100">
	            <div class="col-auto h-100">
	                <form action="usuarios/authentication" method="POST">
	                    <h3 class="text-info">Login</h3>
	                    <label for="">Usuario</label>
	                    <input type="text" name="usuario" placeholder="ingrese su usuario">
	                    <label for="">Contraseña</label>
	                    <input type="text" name="clave" placeholder="Ingrese su contraseña">
	                    <button class="btnLogin" type="submit">Enviar</button>
	                </form>
	            </div>
	        </div>
	    	</div>`;
            renderLogin(data);
        });

    }

    function mostrarViajes() {
        CRUDGENERICO.getModularAuthorization('viajes/', USUARIO.token).then(resp => {
            console.log(resp);
            renderList(resp);
        }, err => {
            console.log(err);
        })
    }

});