from class1 import Login, User, Passenger, Driver, Car

def doesUserExist(email: str) -> bool:
    # Check if the user exists in the database
    # Return True if exists, False otherwise
    pass

# ---------------------------------------------------------------------------
# Sign-up
# ---------------------------------------------------------------------------

def sign_up_process():
    # 1. valid name & email input
    # 2. doesUserExist(email) -> bool
    # 3. if false, verifyEmail(email)
        # 4. add new User to database
        # 5. password reset
    # 3. if true, prompt user to log_in_process()


# ---------------------------------------------------------------------------
# Log-in (ForgotPassword button always available)
# ---------------------------------------------------------------------------

def log_in_process():
    # 1. valid email input
    # 2. password input
    # 3. doesUserExist(email) -> bool
    # 4. if false, prompt user to sign_up_process()
    # 4. if true, verifyPassword(email, password) -> bool
    # 5. if false, prompt user to reset password (3-5 attempts)
        # 6. if 3-5 attempts fail, showCAPTCHA()
    # 5. if true, log user in


# ---------------------------------------------------------------------------
# Forgot Password button always available
# ---------------------------------------------------------------------------

def forgot_password_process():
    # 1. valid email input
    # 2. doesUserExist(email) -> bool
    # 3. if false, prompt user to sign_up_process()
    # 4. if true, verifyEmail(email)
        # 5. reset password