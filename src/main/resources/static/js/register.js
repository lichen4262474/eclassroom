 var termOfUseCheck = function () {
        document.getElementById("register").disabled =
          !document.getElementById("terms-of-use").checked;
      };

      var repeatPasswordCheck = function () {
        var password = document.getElementById("password");
        var repeatPassword = document.getElementById("repeat-password");
        if (password.value != repeatPassword.value) {
          repeatPassword.setCustomValidity("different password");
        } else {
          repeatPassword.setCustomValidity("");
        }
      };

      document.addEventListener("change", termOfUseCheck);
      document.addEventListener("change", repeatPasswordCheck);