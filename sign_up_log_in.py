from class1 import Login, User, Passenger, Driver, Car


def doesUserExist(email: str, pswd: str = None) -> bool:
    # Check if the user exists as a Login in the database
    # If pswd is provided, also check if the password matches
    # Return True if user exists (and password matches if provided), False otherwise
    if pswd is None:
        if email in Login.logins: return True
        else: return False
    else:
        if email in Login.logins and Login.logins[email] == pswd: return True
        else: return False

    # double check Login.logins dictionary for email and password match actually WORKS

def isEmailVerified(email: str) -> bool:
    # Check if the user's email is verified
    # Return True if verified, False otherwise
    if email in Login.logins:
        user = Login.logins[email]
        return user.verifiedStudentEmail
    return False


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
    pass


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
    pass


# ---------------------------------------------------------------------------
# Forgot Password button always available
# ---------------------------------------------------------------------------

def forgot_password_process():
    # 1. valid email input
    email = input("Enter your email for password reset: ") # Will have UI implementation later

    # 2. doesUserExist(email) -> bool
    # 3. if false, prompt user to sign_up_process()
    # 4. if true, resetPassword(email)
    if doesUserExist(email): reset_password_process(email)
    else:
        print("Email does not exist. Please sign up first.")
        sign_up_process()

def reset_password_process(email: str):
    # 1. send password reset link to email

    # 2. user clicks link and enters new password
    # 3. checkValidPassword(new_password) -> bool
    # 4. if false, prompt user to re-enter new password
    # 5. if true, update password in database
    while True:
        new_password = input("Enter your new password: ") # Will have UI implementation later
        if checkValidPassword(new_password):
            # Update password in database (not implemented)
            print("Password has been reset successfully.")
            break
        else:
            print("Invalid password. Please try again.")
    log_in_process()  # Prompt user to log in after password reset
    

# ---------------------------------------------------------------------------
# Input validation checks
# ---------------------------------------------------------------------------

def checkValidName(name: str) -> bool:
    # Check if the name is valid (e.g., not empty, no special characters)
    # Return True if valid, False otherwise
    pass

def checkValidEmail(email: str) -> bool:
    # Check if the email is valid <name>@<uniDomain> where <uniDomain> ends in .ac.uk
    # Return True if valid, False otherwise
    
    # Check if email contains '@' and ends with '.ac.uk'
    if '@' in email and email.endswith('.ac.uk'):
        # Split the email into name and domain parts
        name_part, domain_part = email.split('@', 1)
        
        # Check if the name part is not empty and does not contain special characters
        if name_part and all(char.isalnum() or char in ('-', '_', '.') for char in name_part):
            
            # Check if the domain part contains a valid university domain (e.g., 'ox.ac.uk', 'cam.ac.uk', etc.)
            return True
    return False
    pass

def checkValidPassword(password: str) -> bool:
    # Check if the password meets security requirements (e.g., length, complexity)
    # Return True if valid, False otherwise
    if len(password) < 8:
        return False
    elif not any(char.isdigit() for char in password):
        return False
    elif not any(char.isupper() for char in password):
        return False
    elif not any(char.islower() for char in password):
        return False
    elif not any(char in "!@#$%^&*()-_=+[{]}\|;:'\",<.>/?`~" for char in password):
        return False
    else: return True