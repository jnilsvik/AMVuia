<html>
<head>
    <title>Title</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="css/misc.css">
</head>
<body>
<jsp:include page="_head_nav.jsp"/>
<div class="page">
    <article class="my-3" id="floating-labels"> <!-- this is temp to genter-->
        <div class="bd-heading sticky-xl-top align-self-start mt-5 mb-3 mt-xl-0 mb-xl-2">
            <h3>Forgot password</h3>
        </div>
        <div>
            <div class="bd-example">
                <form action="">
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="nPassword1" placeholder="Password">
                        <label for="nPassword1">New password</label>
                    </div>
                    <div class="form-floating mb-3">
                        <input type="password" class="form-control" id="nPassword2" placeholder="Password">
                        <label for="nPassword2">Confirm</label>
                    </div>
                    <div class="col-12">
                        <button class="btn btn-primary" type="submit">Submit</button>
                    </div>
                    <div style="text-align:center">
                        <a href='register'>Don't have an account? Register here!</a>
                    </div>
                </form>
            </div>
        </div>
    </article>
</div>