import { AppBar, Toolbar, Button, Typography} from '@mui/material';
import { Home} from '@mui/icons-material';

function Header() {
  return (
    <AppBar position="static">
        <Toolbar>
        <Button color={"secondary"}>
          <Home fontSize="large" />
        </Button>
        <Typography variant="h5" >
          Simple Ticketing System
        </Typography>        
        </Toolbar>
    </AppBar>
  );
}

export default Header;
