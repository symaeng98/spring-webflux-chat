const gfnFetch = (url, type, param)=>{
    console.log("============= gfnFetch start =============");
    let method = type;
    let headers = {
        'API': true
    }

    if(type == 'MULTIPART'){
        method = 'POST';
    }else{
        headers['Content-Type'] = 'application/json';
    }

    let options = {
        method: method,
        headers: headers
    };
    
    if((type === 'POST' || type === 'PUT') && param){
        options.body = JSON.stringify(param);
    }else if(type == 'MULTIPART'){
        options.body = param;
    }

    console.log('options', options);
    return new Promise((resolve, reject)=>{
        fetch(url, options).then((res)=>{
            if(res.ok){
                res.json()
                .then((data)=>{
                    console.log("response : ok", data);
                    resolve(data);
                    console.log("============= gfnFetch end =============");
                });
            }else if(res.status == '401'){
                res.json()
                .then((data)=>{
                    console.log("response : 401", data);
                    openAlert(data.detail)
                    .then(()=>{
                        console.log("============= gfnFetch end =============");
                        window.location.href = '/login';
                    });
                });
            }else{
                res.json()
                .then((data)=>{
                    console.log("response : "+ res.status, data);
                    openAlert(data.detail)
                    .then(()=>{
                        console.log("============= gfnFetch end =============");
                        reject(res.status);
                    });
                })
            }
        });
    });
}

const gfnGetFetch = (url, param)=>{
    return gfnFetch(url, 'GET', param);
}

const gfnPostFetch = (url, param)=>{
    return gfnFetch(url, 'POST', param);
}

const gfnMultipartFetch = (url, param)=>{
    return gfnFetch(url, 'MULTIPART', param);
}

const generateElement = (html) => {
    const template = document.createElement('template');
    template.innerHTML = html.trim();
    return template.content.firstElementChild;
}
  
const openConfirm = (content) => {
    return new Promise((resolve)=>{
        const modalTag = generateElement(`
        <div class="modal fade show" tabindex="-1" >
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5">CONFIRM</h1>
                <button type="button" class="btn-close" data-modal-btn=cancel></button>
                </div>
                <div class="modal-body">
                ${content}
                </div>
                <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-modal-btn="cancel">취소</button>
                <button type="button" class="btn btn-primary" data-modal-btn="confirm">확인</button>
                </div>
            </div>
            </div>
        </div>`);
        const backdropTag = generateElement('<div class="modal-backdrop fade show"></div>');

        modalTag.querySelectorAll("[data-modal-btn=cancel]").forEach((el)=>{
        el.addEventListener('click', (e)=>{
            e.preventDefault();
            closeModal(el);
            resolve(false);
        });
        });
        
        modalTag.querySelectorAll("[data-modal-btn=confirm]").forEach((el)=>{
        el.addEventListener('click', (e)=>{
            e.preventDefault();
            closeModal(el);
            resolve(true);
        });
        });

        document.body.append(modalTag);
        document.body.append(backdropTag);
        
        modalTag.style.display = 'block';
        
    });

}

const openAlert = (content) => {
    return new Promise((resolve)=>{
        const modalTag = generateElement(`
        <div class="modal fade show" tabindex="-1" >
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5">ALERT</h1>
                <button type="button" class="btn-close" data-modal-btn=cancel></button>
                </div>
                <div class="modal-body">
                ${content}
                </div>
                <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-modal-btn="cancel">확인</button>
                </div>
            </div>
            </div>
        </div>`);
        const backdropTag = generateElement('<div class="modal-backdrop fade show"></div>');

        modalTag.querySelectorAll("[data-modal-btn=cancel]").forEach((el)=>{
        el.addEventListener('click', (e)=>{
            e.preventDefault();
            closeModal(el);
            resolve(false);
        });
        });
        
        document.body.append(modalTag);
        document.body.append(backdropTag);
        
        modalTag.style.display = 'block';
        
    });

}

const openPopup = async(screenId, fnCallback)=>{
    let html = '';
    const res = await fetch('popup');
    // debugger;
    if(res.ok) html = await res.text();

    console.log(html);

    return new Promise((resolve)=>{
        const modalTag = generateElement(`
        <div class="modal fade show" tabindex="-1" >
            <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                <h1 class="modal-title fs-5">${screenId}</h1>
                <button type="button" class="btn-close" data-modal-btn=cancel></button>
                </div>
                <div class="modal-body">
                ${html}
                </div>
                <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-modal-btn="cancel">확인</button>
                </div>
            </div>
            </div>
        </div>`);
        const backdropTag = generateElement('<div class="modal-backdrop fade show"></div>');

        modalTag.querySelectorAll("[data-modal-btn=cancel]").forEach((el)=>{
        el.addEventListener('click', (e)=>{
            e.preventDefault();
            closeModal(el);
            resolve(false);
        });
        });
        
        document.body.append(modalTag);
        document.body.append(backdropTag);
        
        modalTag.style.display = 'block';
        
    });
}

const closeModal = (el) => {
    el.closest('.modal').remove();
    document.querySelector('.modal-backdrop').remove();
}

const getPageInfo = (totalCount, currentPageNum,pageSize) => {
    const pagesPerBlock = 5;
    const postsPerPage = pageSize;
    let totalLastPageNum = 0;
    let totalPostCount = totalCount;

    if(totalPostCount == 0){
        totalLastPageNum = 1;
    }else{
        totalLastPageNum = parseInt(Math.ceil(totalPostCount/postsPerPage));
    }

    let blockLastPageNum = totalLastPageNum;
    let blockFirstPageNum = 1;
    const mod = totalLastPageNum % pagesPerBlock;
    
    if(totalLastPageNum - mod >= currentPageNum) {
        blockLastPageNum = parseInt(Math.ceil(currentPageNum/pagesPerBlock) * pagesPerBlock);
        blockFirstPageNum = blockLastPageNum - (pagesPerBlock - 1);

    }else{
        blockFirstPageNum = parseInt(Math.ceil(currentPageNum/pagesPerBlock) * pagesPerBlock) - (pagesPerBlock -1);
    }

    let pageList = [];
    for(let i=blockFirstPageNum; i<=blockLastPageNum; i++){
        pageList.push(i);
    }
    const prevPageNum = currentPageNum - pagesPerBlock > 0 ? currentPageNum - pagesPerBlock : 1;
    const nextPageNum = currentPageNum + pagesPerBlock < totalLastPageNum ? currentPageNum + pagesPerBlock : blockFirstPageNum + pagesPerBlock;

    return {
        isPrevExist: currentPageNum > pagesPerBlock,
        isNextExist: blockLastPageNum != 1 ? blockLastPageNum != totalLastPageNum : false,
        totalLastPageNum : totalLastPageNum,
        blockLastPageNum: blockLastPageNum,
        blockFirstPageNum: blockFirstPageNum,
        currentPageNum: currentPageNum,
        totalPostCount: totalPostCount,
        pagesPerBlock: pagesPerBlock,
        postsPerPage: postsPerPage,
        pageList: pageList,
        prevPageNum: prevPageNum,
        nextPageNum: nextPageNum
    }
}

const getPagination = (totalPostCount, currentPageNum,pageSize, targetId) => {
    const pageInfo = getPageInfo(totalPostCount, currentPageNum,pageSize);
    console.log("pageInfo", pageInfo);

    let strPage = `<ul class="pagination">
    <li class="page-item">
    <a class="page-link ${pageInfo.isPrevExist ? '' : 'disabled'}" href="#" onclick="fnJsPage(${pageInfo.prevPageNum})">
    <span>&laquo;</span>
    </a></li>`

    for(let n of pageInfo.pageList){
        
        strPage += `<li class="page-item ${currentPageNum == n ? 'active' : ''}">
                    <a class="page-link" href="#" onclick="fnJsPage(${n})">${n}</a></li>`;
    }

    strPage += `<li class="page-item">
    <a class="page-link ${pageInfo.isNextExist ? '' : 'disabled'}" href="#" onclick="fnJsPage(${pageInfo.nextPageNum})">
    <span>&raquo;</span>
    </a></li></ul>`;
    
    document.getElementById(targetId).innerHTML = strPage;

    
}