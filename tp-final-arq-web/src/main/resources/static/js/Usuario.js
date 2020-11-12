class Usuario extends CRUDGenerico {
    constructor() {
        super('usuarios/');
        this.container = document.querySelector('.main');
		this.token = sessionStorage.getItem('token');
		if(this.token != null){
			this.getHome()
		}
    }
	
	getAuthentication(authentication) {
		console.log(authentication);
		let token = authentication.token;
		if(authentication.status === "Success") {
			console.log(typeof token);
			sessionStorage.setItem("token", token);
			this.token = token;
			this.getHome();
		}
	}
	
	async postModular(uri, data) {
        const response = await fetch(this.BASEURI + '/' + this.RESOURCE + uri, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
				'Authorization' : this.token
            },
            body: JSON.stringify(data)
        });
	    if (response.ok) {
            return await response.json();		
		} else {
            const error = await response.text();
            //return Promise.reject(error);
			return 'error';
        }
    }
	
	async getHome() {
		let url = this.BASEURI + '/' + "home/";
		console.log(url);
		const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json',
				'Authorization' : this.token
            },
//            body: JSON.stringify(data)
        });
		if (response.ok) {
			let res = await response;
            res = await res.text();
			document.querySelector('#bodyContainer').innerHTML = res;
			console.log(res);
			return res;
		}
 		if (!response.ok) {
            const error = await response.text();
            return Promise.reject(error);
        }
	}
	
	readEvents(){
		
		document.querySelector('click', )
		
	}
	
	
	
}