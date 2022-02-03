from time import sleep

from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC

if __name__ == '__main__':
    driver = webdriver.Chrome()
    driver.maximize_window()
    driver.get("http://localhost:4200")
    gp_select = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '/html/body/app-root/app-student-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-graduation-process/app-state/app-graduation-processes-grid/div/app-graduation-process-card/mat-card/mat-card-actions/button'))
    )
    gp_select.click()

    applied_details = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '//*[@id="mat-tab-label-0-1"]'))
    )
    applied_details.click()

    sleep(3)
    subject_details = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH, '/html/body/app-root/app-student-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/div/app-subject/mat-card/mat-tab-group/div/mat-tab-body[2]/div/div/app-subject-table/div/table/tbody/tr[1]/td[5]/button'))
    )
    subject_details.click()

    sleep(2)
    decline_button = WebDriverWait(driver, 10).until(
        EC.presence_of_element_located((By.XPATH,
                                        '/html/body/app-root/app-student-view/app-sidenav/mat-sidenav-container/mat-sidenav-content/app-toolbar/mat-toolbar/span[3]/button[2]'))
    )
    decline_button.click()


