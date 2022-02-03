from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

if __name__ == '__main__':
    driver = webdriver.Chrome()
    driver.maximize_window()
    driver.get("http://localhost:4200/supervisor")
    gp_select = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH,
                                        '/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-supervisor-graduation-processes/app-state/app-graduation-processes-grid/div/app-graduation-process-card/mat-card/mat-card-actions/button'))
    )
    gp_select.click()

    create_subject = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH,
                                        '/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/app-toolbar/mat-toolbar/span[3]/button'))
    )
    create_subject.click()

    topic_in_polish = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-create-subject/app-state/form/div/div[1]/mat-card/mat-card-content/mat-form-field[1]/div/div[1]/div[3]/input'))
    )
    topic_in_polish.send_keys("Przykładowy temat po polsku")

    topic_in_english = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '//*[@id="mat-input-3"]'))
    )
    topic_in_english.send_keys("Example topic in english")

    objective_in_polish = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-create-subject/app-state/form/div/div[1]/mat-card/mat-card-content/mat-form-field[3]/div/div[1]/div[3]/textarea'))
    )
    objective_in_polish.send_keys("Przykładowy cel pracy dyplomowej")

    objective_in_english = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-create-subject/app-state/form/div/div[1]/mat-card/mat-card-content/mat-form-field[4]/div/div[1]/div[3]/textarea'))
    )
    objective_in_english.send_keys("Example objective in english")

    realization_language = driver.find_element(by=By.XPATH, value='/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-create-subject/app-state/form/div/div[1]/mat-card/mat-card-content/mat-form-field[5]/div/div[1]/div[3]')
    realization_language.click()

    polish_option = driver.find_element(by=By.XPATH, value='/html/body/div[2]/div[2]/div/div/div/mat-option[2]/span')
    polish_option.click()

    checkbox = driver.find_element(by=By.XPATH, value='/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-create-subject/app-state/form/div/div[2]/mat-card/mat-card-content/span/mat-checkbox/label/span[1]')
    checkbox.click()

    number_of_realisers = driver.find_element(by=By.XPATH, value='/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-create-subject/app-state/form/div/div[2]/mat-card/mat-card-content/mat-form-field/div/div[1]/div[3]/input')
    number_of_realisers.send_keys('2')

    create_button = driver.find_element(by=By.XPATH, value='/html/body/app-root/app-supervisor-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/app-toolbar/mat-toolbar/span[3]/button')
    create_button.click()
