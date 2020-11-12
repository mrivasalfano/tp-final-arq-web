"use strict"

document.addEventListener("DOMContentLoaded", () => {
    const USUARIO = new Usuario();
	
    // --------------------------USUARIO--------------------------
    document.querySelector('.btnLogin').addEventListener('click', (e) => {
	e.preventDefault();
        let nombre = document.querySelector("[name='usuario']").value;
        let clave = document.querySelector("[name='clave']").value;
		let data = {
			"nombre" : nombre,
			"clave" : clave
		}
		USUARIO.postModular("authentication/", data)
            .then(authentication => USUARIO.getAuthentication(authentication));
    });
});
